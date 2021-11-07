package org.fpeterek.ti.derivationtrees.cfg

object Grammar {
  def apply(grammarRules: (NonTerminal, RuleSet)*) = new Grammar(grammarRules:_*)

  def empty = new Grammar
}

class Grammar(grammarRules: (NonTerminal, RuleSet)*) {

  val rules: Map[NonTerminal, RuleSet] = grammarRules.toMap

  def apply(str: String): Option[RuleSet] = rules.find(_._1.value == str) match {
    case Some(value) => Some(value._2)
    case None => None
  }

  def apply(nt: NonTerminal): Option[RuleSet] = rules.get(nt)

  lazy val nonTerminals: List[NonTerminal] = rules.keys.toList

  lazy val terminals: List[Terminal] = rules.values.flatMap { ruleset =>
    ruleset.rules.flatMap { rule =>
      rule.expansionList.filter {
        _ match {
          case _: Terminal => true
          case _ => false
        }
      }.map(_.asInstanceOf[Terminal])
    }
  }.toList

  override def toString: String = rules
    .map(pair => f"${pair._1} -> ${pair._2}")
    .mkString("\n")
}
