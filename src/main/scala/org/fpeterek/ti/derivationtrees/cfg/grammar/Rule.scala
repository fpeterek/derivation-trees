package org.fpeterek.ti.derivationtrees.cfg.grammar

import scala.language.postfixOps

object Rule {
  def apply(grammarAtom: GrammarAtom): Rule = new Rule(List(grammarAtom))
  def empty: Rule = new Rule(List())

  val FormRule: Rule = new Rule(List())
  val Epsilon: Rule = new Rule(List(Terminal("")))
}

class Rule private(val expansionList: List[GrammarAtom]) {
  def *(grammarAtom: GrammarAtom) = new Rule(expansionList :+ grammarAtom)

  def isEmpty: Boolean = expansionList.isEmpty

  def matches(atoms: Seq[GrammarAtom]): Boolean = expansionList == atoms

  override def toString: String = expansionList.mkString(" * ")
}
