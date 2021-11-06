package org.fpeterek.ti.derivationtrees.cfg

import scala.language.postfixOps

object Rule {
  def apply(grammarAtom: GrammarAtom): Rule = new Rule(List(grammarAtom))

  val FormRule: Rule = new Rule(List())
  val Epsilon: Rule = new Rule(List(Terminal("")))
}

class Rule private(val expansionList: List[GrammarAtom]) {
  def *(grammarAtom: GrammarAtom) = new Rule(expansionList :+ grammarAtom)

  def isEmpty: Boolean = expansionList.isEmpty
}
