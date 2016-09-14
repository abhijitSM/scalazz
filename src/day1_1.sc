import scalaz._
import Scalaz._
import scalaz.syntax.Ops

1 assert_=== 1 // Equal

1 ?|? 2 // Ordering

3.show // Cord apparently is a purely functional data structure for potentially long Strings.
3.shows

"print me".println

// Enum

'a' to 'e'
3 |-> 5

//implicitly[Enum[Double]].max

sealed trait TrafficLight
case object Red extends TrafficLight
case object Yellow extends TrafficLight
case object Green extends TrafficLight

implicit val TrafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)

//Red === Yellow fail Equal is invariant

// Functors
trait Functor[F[_]] { self =>
  def map2[A,B](fa : F[A])(f : A => B) : F[B]
}

object Functor {
  implicit def listFunctor : Functor[List] = new Functor[List] {
    override def map2[A, B](fa: List[A])(f: (A) => B): List[B] = fa map f
  }
}

trait FunctorOps[F[_], A] extends Ops[F[A]] {
  def map2[B](f: A => B)(implicit F : Functor[F]) : F[B] = F.map2(self)(f)
}

object FunctorOps {
 implicit def apply[F[_], A](v : F[A]) : FunctorOps[F, A] = new FunctorOps[F, A] {
    override def self: F[A] = v
  }
}

import FunctorOps._

List(1,2,3).map2(_ + 1)

