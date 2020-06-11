package com.github.pgerhard

import cats.kernel.Order
import org.joda.time.DateTime

import scala.util.Random

object OrderDemo {

  def demo(): Unit = {
    val maria = Person("Maria", "Gerhard")
    val laura = Person("Laura", "Luiz")
    val yann = Person("Yann", "Simon")
    val peter = Person("Peter", "Gerhard")

    // weird way of shuffling - but for the fun of it :)
    // how to properly use OrderToOrderingConversion?
    val persons = Seq(maria, laura, yann, peter).sorted(randomOrder[Person].toOrdering)
    val ordered = persons.sorted(Order[Person].toOrdering)

    println(ordered.map(p => s"${p.lastName} ${p.firstName}"))
    println(isOrdered(ordered))
  }

  @scala.annotation.tailrec
  private def isOrdered[A: Order](as: Seq[A]): Boolean = as match {
    case head :: tail if tail.forall(Order[A].compare(head, _) >= 0) => isOrdered(tail)
    case _ => true
  }

  case class Person(firstName: String, lastName: String, dateOfBirth: Option[DateTime] = None)

  object Person {
    /**
     * Result of comparing `x` with `y`. Returns an Int whose sign is:
     * - negative iff `x < y`
     * - zero     iff `x = y`
     * - positive iff `x > y`
     */
    implicit val lastNameFirstOrder: Order[Person] = new Order[Person] {
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

  // This is just a joke, probably not according to laws
  def randomOrder[A]: Order[A] = new Order[A] {
    override def compare(x: A, y: A): Int = Random.nextInt(3) - 1
  }
}
