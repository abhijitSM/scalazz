import scalaz._
import Scalaz._

trait Kilogram

def kilogram[A](a: A) : A @@ Kilogram = Tag.apply(())[A, Kilogram](a)

val mass: scalaz.@@[Double, Kilogram] = kilogram(20.0)

Tag.unwrap(mass)

Scalaz.ToSemigroupOps(Tags.Multiplication.apply(10))(scalaz.Scalaz.intMultiplicationNewType).|+|(Monoid.apply()[Int @@ Tags.Multiplication].zero)

Scalaz.ToSemigroupOps((Ordering.LT: Ordering))(scalaz.Ordering.orderingInstance).|+|((Ordering.GT: Ordering))
