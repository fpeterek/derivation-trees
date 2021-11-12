package org.fpeterek.ti.derivationtrees.cfg.grammar

object Terminal {
  def apply(value: String): Terminal = new Terminal(value)
  def epsilon: Terminal = new Terminal("")
}

class Terminal(value: String) extends GrammarAtom(if (value == "ε") "" else value) {
  override def toString: String = value.isEmpty match {
    case true => "ε"
    case _ => value
  }
}
