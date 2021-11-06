package org.fpeterek.ti.derivationtrees.cfg

object RuleSet {
  def apply(rule: Rule) = new RuleSet(List(rule))
}

class RuleSet private(ruleList: List[Rule]) {
  val rules: List[Rule] = ruleList.filterNot(_.isEmpty)

  def |(rule: Rule) = new RuleSet(rules :+ rule)
  def |(other: RuleSet) = new RuleSet(rules ++ other.rules)

}
