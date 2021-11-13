package org.fpeterek.ti.derivationtrees

import org.fpeterek.ti.derivationtrees.cfg.analyzers.CykAnalyzer.GrammarGenerates
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
      Start  -> ((Zero * Right) | Epsilon),
      Left   -> ((Zero * Right) | FormRule),
      Right  -> ((Left * One) | one),
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

    println(s"Grammar 1 generates 000111: ${grammar generates "000111"}")
    println(s"Grammar 1 generates 01: ${grammar generates "01"}")
    println(s"Grammar 1 generates 0011: ${grammar generates "0011"}")
    println(s"Grammar 1 generates 0000111: ${grammar generates "0000111"}")
    println(s"Grammar 1 generates 00111: ${grammar generates "00111"}")
    println(s"Grammar 1 generates 010101: ${grammar generates "010101"}")
    println(s"Grammar 1 generates 1100: ${grammar generates "1100"}")
    println(s"Grammar 1 generates epsilon: ${grammar generates ""}")

    println(s"Grammar 2 generates 1100: ${g2 generates "1100"}")
    println(s"Grammar 2 generates 1001101: ${g2 generates "1001101"}")

    println(s"Grammar 3 generates a+b: ${g3 generates "a+b"}")
    println(s"Grammar 3 generates a+b.a: ${g3 generates "a+b.a"}")
    println(s"Grammar 3 generates a++b.a: ${g3 generates "a++b.a"}")
    println(s"Grammar 3 generates a*+b.a: ${g3 generates "a*+b.a"}")
    println(s"Grammar 3 generates a*+b*.a*: ${g3 generates "a*+b*.a*"}")
    println(s"Grammar 3 generates a*+b.*.a*: ${g3 generates "a*+b.*.a*"}")
    println(s"Grammar 3 generates a*+b.*a: ${g3 generates "a*+b.*a"}")
    println(s"Grammar 3 generates a*+b*+a*.b*: ${g3 generates "a*+b*+a*.b*"}")
  }

}
