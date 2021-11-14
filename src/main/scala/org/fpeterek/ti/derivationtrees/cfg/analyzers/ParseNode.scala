package org.fpeterek.ti.derivationtrees.cfg.analyzers

import org.fpeterek.ti.derivationtrees.cfg.grammar.NonTerminal

case class ParseNode(nt: NonTerminal, subnodes: Seq[(ParseNode, ParseNode)]) {
  override def toString: String = nt.toString
}
