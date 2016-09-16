import scalaz._
import Scalaz._
import scala.language.higherKinds

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
// <*> <* *>
//trait Apply[F[_]] {
//  def ap[A, B](fa: => F[A])(f: => F[A => B]): F[B]
//}

1.some <*> {(_:Int) + 1}.some
1.point[List] <*> {(_:Int) + 1}.point[List]
1.point[List] *> {(_:Int) + 1}.point[List]
1.point[List] <* {(_:Int) + 1}.point[List]

none <* 2.some
2.some *> none

val f = { 9.some <*> {(_: Int) + (_: Int)}.curried.some }
3.some <*> f

(3.some |@| 5.some){_ + _}

val res = Apply[Option].lift2((_: Int) :: (_: List[Int]))
res(3.some, 1.point[List].some)

type Function1Int[A] = ({ type l[A] = Function1[Int, A]})#l[A]

def ls : List[Function1Int[Int]] = List((_: Int) + 3, (_: Int) + 4, (_: Int) + 5)

def sequenceA[F[_]: Applicative, A](list: List[F[A]]): F[List[A]] = list match {
  case Nil     => (Nil: List[A]).point[F]
  case x :: xs => (x |@| sequenceA(xs)) {_ :: _}
}

val s = sequenceA(ls)
s(3)










