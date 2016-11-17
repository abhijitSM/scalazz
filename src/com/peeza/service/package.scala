package com.peeza

import scalaz.Free

package object service {
  type ServiceResponse[A] = Free[PizzaServiceOp, A]
}
