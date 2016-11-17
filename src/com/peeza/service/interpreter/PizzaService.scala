package com.peeza.service.interpreter

import scalaz.Scalaz._
import scalaz._

import com.peeza.model
import com.peeza.model._
import com.peeza.service._

sealed trait Interpreter {
  type Response[A] = NonEmptyList[String] \/ A

  def apply[A](action: ServiceResponse[A]): Response[A]
}

object PizzaServiceInterpreter extends Interpreter {

  val step: PizzaServiceOp ~> Response = new (PizzaServiceOp ~> Response) {
    override def apply[A](fa: PizzaServiceOp[A]): Response[A] = fa match {
      case Start(cat) =>
        model.Category.fromName(cat).map(c => Pizza(category = Some(c)).right).getOrElse(NonEmptyList(s"Invalid Category : $cat").left)
      case ChooseBase(p, b) =>
        Base.fromName(b).map(bs => p.copy(base = Some(bs)).right).getOrElse(NonEmptyList(s"Invalid Base : $b").left)
      case AddToppings(p, ts) =>
        val tops = ts.flatMap(Topping.fromName)
        p.category match {
          case Some(Standard) if tops.exists(_.isInstanceOf[DeluxeTopping]) => NonEmptyList("Standard Pizza cannot have Deluxe toppings").left
          case Some(Standard) if tops.size > 3 => NonEmptyList("Standard Pizza cannot have more than 3 toppings").left
          case Some(Standard) => p.copy(toppings = tops).right
          case Some(Deluxe) if tops.size > 5 => NonEmptyList("Deluxe Pizza cannot have more than 5 toppings").left
          case Some(Deluxe) => p.copy(toppings = tops).right
          case None => NonEmptyList("Invalid Pizza Category").left[Pizza]
        }
    }
  }

  override def apply[Pizza](action: ServiceResponse[Pizza]): Response[Pizza] = action.foldMap(step)
}

object PizzaService extends PizzaService
