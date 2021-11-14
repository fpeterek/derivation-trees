package org.fpeterek.ti.derivationtrees.cfg.analyzers

import org.fpeterek.ti.derivationtrees.cfg.grammar.GrammarImplicits.AtomConverter
import org.fpeterek.ti.derivationtrees.cfg.grammar.{Grammar, NonTerminal, Rule, Terminal}

import scala.collection.mutable

object CykAnalyzer {
  def generates(grammar: Grammar, word: String): Boolean = new CykAnalyzer(grammar, word).generates
  def parseTrees(grammar: Grammar, word: String): Int = new CykAnalyzer(grammar, word).parseTrees

  implicit class GrammarGenerates(grammar: Grammar) {
    def generates(word: String): Boolean = CykAnalyzer.generates(grammar, word)
    def parseTrees(word: String): Int = CykAnalyzer.parseTrees(grammar, word)
  }

  implicit class WordIsGenerated(word: String) {
    def isGeneratedBy(grammar: Grammar): Boolean = CykAnalyzer.generates(grammar, word)
    def parseTreesBy(grammar: Grammar): Int = CykAnalyzer.parseTrees(grammar, word)
  }
}

class CykAnalyzer private(val grammar: Grammar, val word: String) {

  private val matrix = mutable.ListBuffer(
    (0 until word.length).map { i =>
      (0 until (word.length-i)).map(_ => Seq[ParseNode]())
    }:_*
  )

  private def contract(terminal: Terminal) = grammar.rules
    .filter(_._2 matches Seq(terminal))
    .keys
    .map(ParseNode(_, List()))
    .toList

  private def fillFromTerminals(): Unit = {
    matrix(0) = word.map(char => contract(char.toString.t))
  }

  private def splits(wordLen: Int) = (1 until wordLen).map { it => (it, wordLen-it) }

  private def contractSplit(split: (Int, Int), begin: Int): List[ParseNode] = {
    val left = matrix(split._1 - 1)(begin)
    val right = matrix(split._2 - 1)(begin + split._1)

    left
      .flatMap { l => right.map((l, _)) }
      .flatMap { nts =>
        grammar
          .rulesFor(List(nts._1.nt, nts._2.nt))
          .map { nt => ParseNode(nt, List(nts)) }
      }
      .toList
  }

  private def contractWord(row: Int, begin: Int) =
    splits(row+1)
      .flatMap { split => contractSplit(split, begin) }
      .toList

  private def contractRow(row: Int): Unit = {
    val wordSize = row+1

    matrix(row) = (0 to (word.length - wordSize))
      .map { begin => contractWord(row, begin) }
  }

  private def contractNonTerminals(): Unit = (1 until word.length).foreach(contractRow)

  private def acceptsEpsilon = grammar.rulesFor(Seq(Terminal.epsilon)).nonEmpty

  private def printMatrix(): Unit = {
    val lines = matrix.map { _.map { _.mkString("(", ", ", ")") } }
    val maxLen = lines.flatMap(_.map(_.length)).max

    lines
      .map { line =>
        line
          .map { exp => s"$exp${" " * (maxLen - exp.length)}" }
          .mkString(" | ")
      }
      .foreach(println)
  }

  private def matrixTop = matrix.last.head
  private def matrixTopNTs = matrix.last.head.map(_.nt)

  private def analyzeWord(): Unit = {
    fillFromTerminals()
    contractNonTerminals()
  }

  private def acceptsWord = {
    analyzeWord()
    // printMatrix()
    matrixTopNTs contains grammar.start
  }

  def generates: Boolean = grammar.nonEmpty && word.isEmpty match {
    case true => acceptsEpsilon
    case false => acceptsWord
  }

  private def countTrees(nodes: Seq[(ParseNode, ParseNode)]): Int = nodes
    .map { tuple => countTrees(tuple._1) * countTrees(tuple._2) }
    .sum

  private def countTrees(node: ParseNode): Int = node.subnodes.isEmpty match {
    case true => 1
    case false => countTrees(node.subnodes)
  }

  private def treeCount: Int = matrixTop.filter(_.nt == grammar.start).map(countTrees).sum

  def parseTrees: Int = if (grammar.isEmpty) {
    0
  } else if (word.isEmpty && acceptsEpsilon) {
    1
  } else {
    analyzeWord()
    // printMatrix()
    treeCount
  }

}
