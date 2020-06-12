package com.github.pgerhard

import cats.kernel.Order
import cats.Order.catsKernelOrderingForOrder
import cats.Show
import cats.implicits.catsStdShowForList

import scala.util.Random

object OrderDemo {

  val ps = Show[List[Person]]

  def demo(): Unit = {
    val maria = Person("Maria", "Gerhard")
    val laura = Person("Laura", "Luiz")
    val yann = Person("Yann", "Simon")
    val peter = Person("Peter", "Gerhard")

    val persons: List[Person] = Random.shuffle(List(maria, laura, yann, peter))
    val ordered: List[Person] = persons.sorted

    println(s"${ps.show(persons)} | isOrdered: ${isOrdered(persons)}")
    println(s"${ps.show(ordered)} | isOrdered: ${isOrdered(ordered)}")
  }

  @scala.annotation.tailrec
  private def isOrdered[A: Order](as: Seq[A]): Boolean = as match {
    case a1 :: a2 :: _ if Order[A].compare(a1, a2) <= 0 => isOrdered(as.tail)
    case a1 :: a2 :: _ if Order[A].compare(a1, a2) > 0 => false
    case _ => true
  }
}
