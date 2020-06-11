package com.github.pgerhard

import cats.Show
import cats.kernel.Eq

object EqDemo {

  def demo(): Unit = {
    val tom = Person("Tom")
    val tin = Person("Tin")
    val ahmed = Person("Muhammad")
    val mustafa = Person("Muhammad")

    println(showIsEqual(tin, tom))
    println(showIsEqual(ahmed, mustafa))
  }

  private def showIsEqual[A: Show : Eq](x: A, y: A): String =
    s"isEqual: ${Show[A].show(x)} and ${Show[A].show(y)} => ${isEqual(x, y)}"

  private def isEqual[A: Eq](x: A, y: A): Boolean = Eq[A].eqv(x, y)

  case class Person(name: String)

  object Person {
    implicit val personEq: Eq[Person] = new Eq[Person] {
      override def eqv(x: Person, y: Person): Boolean = x.name == y.name
    }

    implicit val personShow: Show[Person] = new Show[Person] {
      override def show(t: Person): String = t.name
    }
  }
}
