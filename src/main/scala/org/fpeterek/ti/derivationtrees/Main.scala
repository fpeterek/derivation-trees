package org.fpeterek.ti.derivationtrees

import org.fpeterek.ti.derivationtrees.cfg.grammar.GrammarImplicits.{AtomConverter, RuleFromAtoms, RuleSetFromRules}
import org.fpeterek.ti.derivationtrees.cfg.grammar.Rule.{Epsilon, FormRule}
import org.fpeterek.ti.derivationtrees.cfg.grammar.{Grammar, GrammarLoader}

import scala.language.postfixOps

object Main {

  val grammar: Grammar = {
    val Start = "Start"n
    val Left = "Left"n
    val Right = "Right"n
    val Zero = "Zero"n
    val One = "One"n
    val zero = "0"t
    val one = "1"t

    Grammar(
      Start  -> ((Zero * Right) | (Zero * One) | Epsilon),
      Left   -> ((Zero * Right) | FormRule),
      Right  -> ((Left * One) | FormRule),
      Zero   -> (zero | FormRule),
      One    -> (one | FormRule),
    )
  }

  def main(args: Array[String]): Unit = {
    grammar.terminals.foreach(t => println(f"Terminal: '${t.value}'"))
    grammar.nonTerminals.foreach(nt => println(f"NonTerminal: '${nt.value}'"))

    val g2 = GrammarLoader.fromFile("grammars/binary.txt")
    val g3 = GrammarLoader.fromFile("grammars/regex.txt")

    println("Grammar 1:")
    println(grammar)
    println("--------------------------------")
    println("Grammar 2:")
    println(g2)
    println("--------------------------------")
    println("Grammar 3:")
    println(g3)
    println("--------------------------------")
  }

}
