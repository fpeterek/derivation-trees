package org.fpeterek.ti.derivationtrees.cfg

object RuleSet {
  def apply(rule: Rule) = new RuleSet(List(rule))
  def empty: RuleSet = new RuleSet(List())
}

class RuleSet private(ruleList: List[Rule]) {
  val rules: List[Rule] = ruleList.filterNot(_.isEmpty)

  def |(rule: Rule) = new RuleSet(rules :+ rule)
  def |(other: RuleSet) = new RuleSet(rules ++ other.rules)

  override def toString: String = rules.mkString(" | ")
}
