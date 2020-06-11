package com.github.pgerhard

import cats.kernel.Semigroup
import cats.Show

object SemigroupDemo {

  def demo(): Unit = {
    val a = Rational(1, 2)
    val b = Rational(2, 3)
    val c = Rational(1, 4)

    val rationals = Seq(a, b, c)
    val combined = Semigroup[Rational].combineAllOption(rationals).getOrElse(Rational.zero)

    println(Show[Rational].show(combined))
    println(Show[Rational].show(Rational(270, 192)))

    import cats.implicits._
    println(Semigroup[String].combineAllOption(Seq("Hello", " ", "Semigroup", "!")).getOrElse(""))
  }

  case class Rational(numerator: Int, denominator: Int)  {
    lazy val gcd: Int = Rational.calcGcd(numerator, denominator)
  }

  object Rational {

    val zero: Rational = Rational(0)

    def apply(numerator: Int): Rational = new Rational(numerator, 1)

    implicit val rationalMultiplicationSemigroup: Semigroup[Rational] = new Semigroup[Rational] {
      override def combine(x: Rational, y: Rational): Rational =
        Rational(x.numerator * y.numerator, x.denominator * y.denominator)
    }

    implicit val rationalShow: Show[Rational] = new Show[Rational] {
      override def show(t: Rational): String =
        s"${t.numerator / t.gcd}/${t.denominator / t.gcd}"
    }

    @scala.annotation.tailrec
    def calcGcd(x: Int, y: Int): Int =
      if (x == 0)
        y
      else if (y == 0)
        x
      else
        calcGcd(y, x % y)
  }
}
