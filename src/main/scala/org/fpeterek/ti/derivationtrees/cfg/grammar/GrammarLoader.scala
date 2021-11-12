package org.fpeterek.ti.derivationtrees.cfg.grammar

import org.fpeterek.ti.derivationtrees.cfg.grammar.GrammarImplicits.AtomConverter

import scala.io.Source

object GrammarLoader {

  implicit class UseSource(src: Source) {
    def use[T](fn: Source => T): T = {
      val result = fn(src)
      src.close()
      result
    }
  }

  private def loadLines(filename: String) = {
    Source.fromFile(filename).use {
      _.getLines().filter(_.nonEmpty).toList
    }
  }

  private def splitLines(lines: List[String]) = {
    lines
      .map(_.split("->"))
      .map { arr => (arr(0).trim, arr(1).split('|').map(_.trim)) }
  }

  private def nonterminals(rules: List[(String, Array[String])]) = rules.map(_._1.n)

  private def toGrammarRule(line: (String, Array[String]), nonterminals: List[NonTerminal]) = {
    val nt = line._1.n

    val rules = line._2
      .map { rule =>
        rule
          .filterNot(_.isSpaceChar)
          .map(_.toString)
          .map { char =>
            nonterminals contains char.n match {
              case true => char.n
              case _ => char.t
            }
          }
          .foldLeft(Rule.empty) { (res, it) => res * it }
      }
      .foldLeft(RuleSet.empty) { (rs, rule) => rs | rule }

    (nt, rules)
  }

  def fromFile(filename: String): Grammar = {

    val rules = splitLines(loadLines(filename))

    val nonTerminals = nonterminals(rules)
    val grammarRules = rules.map(rule => toGrammarRule(rule, nonTerminals))

    Grammar(grammarRules: _*)
  }

}
