package org.fpeterek.ti.derivationtrees

import org.fpeterek.ti.derivationtrees.cfg.{GrammarAtom, RuleSet}
import org.fpeterek.ti.derivationtrees.cfg.GrammarExpansion.{AtomConverter, RuleFromAtoms, RuleSetFromRules}
import org.fpeterek.ti.derivationtrees.cfg.Rule.{FormRule, Epsilon}

import scala.language.postfixOps

object Main {

  val grammar: Map[GrammarAtom, RuleSet] = {
    val Start = "Start"n
    val Expand = "Expand"n
    val Zero = "Zero"n
    val One = "One"n
    val zero = "0"t
    val one = "1"t

    Map(
      Start  -> ((Zero * Expand) | (Zero * One) | Epsilon),
      Expand -> ((Start * One) | FormRule),
      Zero   -> (zero | FormRule),
      One    -> (one | FormRule),
    )
  }

  def main(args: Array[String]): Unit = {
  }

}
