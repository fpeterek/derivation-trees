package org.fpeterek.ti.derivationtrees.cfg

class GrammarAtom(val value: String) {
  override def hashCode(): Int = value.hashCode
}
