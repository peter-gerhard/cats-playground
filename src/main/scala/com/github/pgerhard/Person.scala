package com.github.pgerhard

import cats.Show
import cats.kernel.{Eq, Order}

case class Person(firstName: String, lastName: String)

object Person {

  implicit val personEq: Eq[Person] = new Eq[Person] {
    override def eqv(x: Person, y: Person): Boolean =
      x.lastName == y.lastName && x.firstName == y.lastName
  }

  implicit val personShow: Show[Person] = new Show[Person] {
    override def show(t: Person): String = s"${t.firstName} ${t.lastName}"
  }

  /**
   * Result of comparing `x` with `y`. Returns an Int whose sign is:
   * - negative iff `x < y`
   * - zero     iff `x = y`
   * - positive iff `x > y`
   */
  implicit val personLastNameFirstOrder: Order[Person] = new Order[Person] {
    override def compare(x: Person, y: Person): Int =
      if (x.lastName < y.lastName)
        -1
      else if (x.lastName > y.lastName)
        1
      else if (x.firstName < y.firstName)
        -1
      else if (x.firstName > y.firstName)
        1
      else
        0
  }
}
