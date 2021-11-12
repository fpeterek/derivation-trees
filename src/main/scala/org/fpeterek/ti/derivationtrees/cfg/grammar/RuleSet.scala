package org.fpeterek.ti.derivationtrees.cfg.grammar

object RuleSet {
  def apply(rule: Rule) = new RuleSet(List(rule))
  def empty: RuleSet = new RuleSet(List())
}

class RuleSet private(ruleList: List[Rule]) {
  val rules: List[Rule] = ruleList.filterNot(_.isEmpty)

  def |(rule: Rule) = new RuleSet(rules :+ rule)
  def |(other: RuleSet) = new RuleSet(rules ++ other.rules)

  def matches(atoms: Seq[GrammarAtom]): Boolean = rules.exists(_ matches atoms)

  override def toString: String = rules.mkString(" | ")
}
