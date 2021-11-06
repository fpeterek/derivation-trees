package org.fpeterek.ti.derivationtrees.cfg

object Terminal {
  def apply(value: String): Terminal = new Terminal(value)
}

class Terminal(value: String) extends GrammarAtom(value)
