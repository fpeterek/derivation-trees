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
      (0 until (word.length-i)).map(_ => List[NonTerminal]())
    }:_*
  )

  private def contract(terminal: Terminal) = grammar.rules
    .filter(_._2 matches Seq(terminal))
    .keys
    .toSet
    .toList

  private def fillFromTerminals(): Unit = {
    matrix(0) = word.map(char => contract(char.toString.t))
  }

  private def splits(wordLen: Int) = (1 until wordLen).map { it => (it, wordLen-it) }

  def contractSplit(split: (Int, Int), begin: Int): List[NonTerminal] = {
    val left = matrix(split._1 - 1)(begin)
    val right = matrix(split._2 - 1)(begin + split._1)

    left
      .flatMap { l => right.map(List(l, _)) }
      .flatMap { nts => grammar.rulesFor(nts) }
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
    matrix.foreach { line =>
      val l = line
        .map(_.mkString("(", ", ", ")"))
        .mkString(" | ")
      println(l)
    }
  }

  private def matrixTop = matrix.last.head

  private def acceptsWord = {
    fillFromTerminals()
    contractNonTerminals()
    // printMatrix()

    matrixTop contains grammar.start
  }

  def generates: Boolean = grammar.nonEmpty && word.isEmpty match {
    case true => acceptsEpsilon
    case false => acceptsWord
  }

  def parseTrees: Int = 0

}
