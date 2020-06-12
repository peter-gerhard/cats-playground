package com.github.pgerhard

import cats.Show
import cats.kernel.Eq

object EqDemo {

  def demo(): Unit = {
    val person1 = Person("Antonije", "Amir")
    val person2 = Person("Theresa", "Letícia")
    val person3 = person1.copy(lastName = "Josephus")

    println(showIsEqual(person1, person2))
    println(showIsEqual(person2, person2))

    // instead of using the general purpose eq for person,
    // we can define an ad-hoc eq for a context (e.g. only first name equality)
    val personFirstNameEq: Eq[Person] = new Eq[Person] {
      override def eqv(x: Person, y: Person): Boolean = x.firstName == y.firstName
    }

    println(showIsEqual(person1, person3)) // false
    println(showIsEqual(person1, person3)(Person.personShow, personFirstNameEq)) // true
  }

  private def showIsEqual[A: Show : Eq](x: A, y: A): String =
    s"isEqual: ${Show[A].show(x)} and ${Show[A].show(y)} => ${Eq[A].eqv(x, y)}"
}
