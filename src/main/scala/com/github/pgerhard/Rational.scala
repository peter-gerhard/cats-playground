package com.github.pgerhard

import cats.kernel.{Monoid, Semigroup}
import cats.Show

case class Rational(numerator: Int, denominator: Int)  {

  override def toString: String = {
    val gcd = Rational.calcGcd(numerator, denominator)
    s"${numerator / gcd}/${denominator / gcd}"
  }
}

object Rational {

  val zero: Rational = Rational(0)
  val one: Rational = Rational(1)

  def apply(numerator: Int): Rational = new Rational(numerator, 1)

  @scala.annotation.tailrec
  def calcGcd(x: Int, y: Int): Int =
    if (x == 0)
      y
    else if (y == 0)
      x
    else
      calcGcd(y, x % y)

  implicit val rationalShow: Show[Rational] = new Show[Rational] {
    override def show(t: Rational): String = t.toString
  }

  // how can I get both in one definition?
  implicit val rationalMultiplicationSemigroup: Semigroup[Rational] = new Semigroup[Rational] {
    override def combine(x: Rational, y: Rational): Rational =
      Rational(x.numerator * y.numerator, x.denominator * y.denominator)
  }

  implicit val rationalMultiplicationMonoid: Monoid[Rational] = new Monoid[Rational] {
    override def empty: Rational = Rational.one

    override def combine(x: Rational, y: Rational): Rational =
      Rational(x.numerator * y.numerator, x.denominator * y.denominator)
  }
}