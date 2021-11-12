package org.fpeterek.ti.derivationtrees.cfg.grammar

object GrammarImplicits {

  implicit class AtomConverter(str: String) {
    def n: NonTerminal = NonTerminal(str)

    def t: Terminal = Terminal(str)
  }

  implicit class RuleFromAtoms(atom: GrammarAtom) {
    def *(other: GrammarAtom): Rule = Rule(atom) * other

    def |(other: Rule): RuleSet = Rule(atom) | other
  }

  implicit class RuleSetFromRules(rule: Rule) {
    def |(other: Rule): RuleSet = RuleSet(rule) | other

    def |(other: GrammarAtom): RuleSet = RuleSet(rule) | Rule(other)
  }

}
