package com.github.pgerhard

/*
  At its core, Kleisli[F[_], A, B] is just a wrapper around the function A => F[B].
  Depending on the properties of the F[_], we can do different things with Kleislis.
  For instance, if F[_] has a FlatMap[F] instance (we can call flatMap on F[A] values),
  we can compose two Kleislis much like we can two functions.

  Source: https://typelevel.org/cats/datatypes/kleisli.html#kleisli-1
 */
object KleisliDemo {

  import cats.FlatMap
  import cats.implicits._

  final case class Kleisli[F[_], A, B](run: A => F[B]) {
    def compose[Z](k: Kleisli[F, Z, A])(implicit F: FlatMap[F]): Kleisli[F, Z, B] =
      Kleisli[F, Z, B](z => k.run(z).flatMap(run))

    def andThen[Z](k: Kleisli[F, B, Z])(implicit F: FlatMap[F]): Kleisli[F, A, Z] =
      Kleisli[F, A, Z](a => run(a).flatMap(k.run))
  }

  def demo(): Unit = {
    // Bring in cats.FlatMap[Option] instance
    import cats.implicits._

    // String => Option[Int]
    val parse: Kleisli[Option, String, Int] =
      Kleisli((s: String) => if (s.matches("-?[0-9]+")) Some(s.toInt) else None)

    // Int => Option[Double]
    val reciprocal: Kleisli[Option, Int, Double] =
      Kleisli((i: Int) => if (i != 0) Some(1.0 / i) else None)

    // String => Option[Double]
    val parseAndReciprocal: Kleisli[Option, String, Double] =
      reciprocal.compose(parse)

    val parseAndReciprocal2: Kleisli[Option, String, Double] =
      parse.andThen(reciprocal)

    println(parseAndReciprocal.run("2"))
    println(parseAndReciprocal2.run("2"))
    println(parseAndReciprocal2.run("0"))
    println(parseAndReciprocal2.run("a"))
  }




}
