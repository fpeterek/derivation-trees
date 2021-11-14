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
    val g4 = GrammarLoader.fromFile("grammars/znon_with_redundancies.txt")

    println("Grammar 1:")
    println(grammar)
    println("--------------------------------")
    println("Grammar 2:")
    println(g2)
    println("--------------------------------")
    println("Grammar 3:")
    println(g3)
    println("--------------------------------")

    println(s"Number of parse trees for word 000111: ${grammar parseTrees "000111"}")
    println(s"Grammar 1, word 01: ${grammar parseTrees "01"}")
    println(s"Grammar 1, word 0011: ${grammar parseTrees "0011"}")
    println(s"Grammar 1, word 0000111: ${grammar parseTrees "0000111"}")
    println(s"Grammar 1, word 00111: ${grammar parseTrees "00111"}")
    println(s"Grammar 1, word 010101: ${grammar parseTrees "010101"}")
    println(s"Grammar 1, word 1100: ${grammar parseTrees "1100"}")
    println(s"Grammar 1, word epsilon: ${grammar parseTrees ""}")

    println(s"Grammar 2, word 1100: ${g2 parseTrees "1100"}")
    println(s"Grammar 2, word 1001101: ${g2 parseTrees "1001101"}")

    println(s"Grammar 3, word a+b: ${g3 parseTrees "a+b"}")
    println(s"Grammar 3, word a+b.a: ${g3 parseTrees "a+b.a"}")
    println(s"Grammar 3, word a++b.a: ${g3 parseTrees "a++b.a"}")
    println(s"Grammar 3, word a*+b.a: ${g3 parseTrees "a*+b.a"}")
    println(s"Grammar 3, word a*+b*.a*: ${g3 parseTrees "a*+b*.a*"}")
    println(s"Grammar 3, word a*+b.*.a*: ${g3 parseTrees "a*+b.*.a*"}")
    println(s"Grammar 3, word a*+b.*a: ${g3 parseTrees "a*+b.*a"}")
    println(s"Grammar 3, word a*+b*+a*.b*: ${g3 parseTrees "a*+b*+a*.b*"}")
    println(s"Grammar 3, word a+a+a+a: ${g3 parseTrees "a+a+a+a"}")
    println(s"Grammar 3, word a+a+a+a+a: ${g3 parseTrees "a+a+a+a+a"}")
    println(s"Grammar 3, word a+a+a+a+a+a: ${g3 parseTrees "a+a+a+a+a+a"}")
    println(s"Grammar 3, word a+a+a+a+a+a+a: ${g3 parseTrees "a+a+a+a+a+a+a"}")
    println(s"Grammar 3, word a+b+a+b+a+b+a+b: ${g3 parseTrees "a+b+a+b+a+b+a+b"}")

    println(s"Grammar 4, word 01: ${g4 parseTrees "01"}")
    println(s"Grammar 4, word 0011: ${g4 parseTrees "0011"}")
    println(s"Grammar 4, word 000111: ${g4 parseTrees "000111"}")
  }

}
