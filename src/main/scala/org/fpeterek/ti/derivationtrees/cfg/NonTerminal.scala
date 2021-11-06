package org.fpeterek.ti.derivationtrees.cfg

object NonTerminal {
  def apply(value: String): NonTerminal = new NonTerminal(value)
}

class NonTerminal(value: String) extends GrammarAtom(value)
