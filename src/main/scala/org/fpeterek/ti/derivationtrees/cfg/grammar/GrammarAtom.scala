package org.fpeterek.ti.derivationtrees.cfg.grammar

abstract class GrammarAtom(val value: String) {
  override def hashCode(): Int = value.hashCode

  override def equals(obj: Any): Boolean =
    obj.getClass == getClass && obj.asInstanceOf[GrammarAtom].value == value

  override def toString: String = value
}
