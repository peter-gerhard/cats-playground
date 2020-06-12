package com.github.pgerhard

import cats.kernel.Monoid

object MonoidDemo {

  def demo(): Unit = {
    val a = Rational(1, 2)
    val b = Rational(2, 3)
    val c = Rational(1, 4)

    val rationals = Seq(a, b, c)
    val combined = Monoid[Rational].combineAll(rationals)

    println(combined)
    println(Rational(270, 192))

    import cats.implicits.catsKernelStdMonoidForString
    println(Monoid[String].combineAll(Seq("Hello", " ", "Monoid", "!")))
  }
}
