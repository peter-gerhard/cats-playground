package com.github.pgerhard

import cats.kernel.Semigroup

object SemigroupDemo {

  def demo(): Unit = {
    val a = Rational(1, 2)
    val b = Rational(2, 3)
    val c = Rational(1, 4)

    val rationals = Seq(a, b, c)
    val combined = Semigroup[Rational].combineAllOption(rationals)

    println(combined)
    println(Rational(270, 192))

    import cats.implicits.catsKernelStdMonoidForString // Monoid extends Semigroup
    println(Semigroup[String].combineAllOption(Seq("Hello", " ", "Semigroup", "!")).getOrElse(""))
  }
}
