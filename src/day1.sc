// Ad-hoc polymorphism

trait Plus[A] {
  def sum(a1: A, a2: A) : A
}

def plus[A: Plus](a1: A, a2: A) : A = implicitly[Plus[A]].sum(a1, a2)

// typeclass style
def plus2[A: Plus](a1: A, a2: A)(implicit plus: Plus[A]) : A = plus.sum(a1, a2)

// eg;
implicit object IntPlus extends Plus[Int] {
  override def sum(a1: Int, a2: Int): Int = a1 + a2
}

assert(plus2(1,2) == 3) // great

// Monoid
trait Monoid[A] {
  def mappend(a1: A, a2: A) : A
  def mZero : A
}

object Monoid {
  implicit val StringMonoid : Monoid[String] = new Monoid[String] {
    override def mappend(a1: String, a2: String): String = a1 + a2
    override def mZero: String = ""
  }

  implicit val IntMonoid : Monoid[Int] = new Monoid[Int] {
    override def mappend(a1: Int, a2: Int): Int = a1 + a2
    override def mZero: Int = 0
  }
}


def sum2[A](xs: List[A])(implicit m: Monoid[A]) : A =  xs.foldLeft(m.mZero)(m.mappend)
// or single implicit can be written to content bound
def sum[A : Monoid](xs: List[A]) : A = {
  val m = implicitly[Monoid[A]]
  xs.foldLeft(m.mZero)(m.mappend)
}

sum2(List("123", "abc"))

// pull out List from sum
trait FoldLeft[F[_]] {
  def foldleft[A, B](xs : F[A], b: B, f : (B,A) => B) : B
}

object FoldLeft {
  implicit val FoldleftList : FoldLeft[List] = new FoldLeft[List] {
    override def foldleft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
  }
}

def sum3[M[A] : FoldLeft , A: Monoid](xs : M[A]) : A = {
  val m = implicitly[Monoid[A]]
  val foldLeft = implicitly[FoldLeft[M]]
 foldLeft.foldleft(xs, m.mZero, m.mappend)
}

sum3(List("a","b","c"))

trait MonoidOp[A] {
  val F: Monoid[A]
  val value: A
  def |+|(a2: A) = F.mappend(value, a2)
}

implicit def toMonoidOp[A : Monoid](a: A) : MonoidOp[A] = new MonoidOp[A] {
  override val value: A = a
  override val F: Monoid[A] = implicitly[Monoid[A]]
}

3 |+| 4
