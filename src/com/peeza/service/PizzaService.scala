package com.peeza.service

import scalaz.Free

import com.peeza.model.Pizza

sealed trait PizzaServiceOp[A]

case class Start private[service] (category: String) extends PizzaServiceOp[Pizza]

case class ChooseBase private[service](pizza: Pizza, base: String) extends PizzaServiceOp[Pizza]

case class AddToppings private[service](pizza: Pizza, toppings: List[String]) extends PizzaServiceOp[Pizza]

trait PizzaService {

  def startOrder(category: String): ServiceResponse[Pizza] = Free.liftF(Start(category))

  def chooseBase(pizza: Pizza, base: String): ServiceResponse[Pizza] = Free.liftF(ChooseBase(pizza, base))

  def addToppings(pizza: Pizza, toppings: List[String]): ServiceResponse[Pizza] = Free.liftF(AddToppings(pizza, toppings))
}

