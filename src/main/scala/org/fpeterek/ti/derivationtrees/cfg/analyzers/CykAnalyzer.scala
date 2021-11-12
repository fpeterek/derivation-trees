package org.fpeterek.ti.derivationtrees.cfg.analyzers

import org.fpeterek.ti.derivationtrees.cfg.grammar.GrammarImplicits.AtomConverter
import org.fpeterek.ti.derivationtrees.cfg.grammar.{Grammar, NonTerminal, Terminal}

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
      val line = (0 until (word.length-i)).map(_ => List[NonTerminal]()).toList

      mutable.ListBuffer[List[NonTerminal]](line:_*)
    }
  )

  private def contract(terminal: Terminal) = mutable.ListBuffer(
    grammar.rules
      .filter(_._2 matches Seq(terminal))
      .keys
      .toSet
      .toList
  )

  private def fillFromTerminals(): Unit = {
    matrix(0) = word.map(char => contract(char.toString.t))
  }

  def generates: Boolean = {
    fillFromTerminals()

    false
  }
  def parseTrees: Int = 0

}
