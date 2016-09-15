import scalaz._
import Scalaz._


// Lifting (A => B) then (F[A] => F[B])
val lifted = Functor[List].lift((_: Int) * 2)
lifted(List(2))

// some fuctors ops
List(1, 2, 3).fpair

List(1, 2, 3).strengthL("x")

// Functor cannot map over multi args without currying
val multi = List(1, 2, 3) map {
  (_: Int) * (_: Int)
}.curried

multi map (_ (3))

// Applicative - wraps any value A to a container without any operations.

//trait Applicative[F[_]] extends Apply[F] { self => def point[A](a: => A): F[A]
//  /** alias for `point` */
//  def pure[A](a: => A): F[A] = point(a) ...
//}

1.point[List]

// Apply ^

