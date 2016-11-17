package com.peeza.app

import scalaz.{-\/, \/-}

import com.peeza.service.interpreter.{PizzaService, PizzaServiceInterpreter}

object Main extends App {

  val pizzaOrder = for {
    p <- PizzaService.startOrder("Standard")
    p2 <- PizzaService.chooseBase(p, "Pan")
    p3 <- PizzaService.addToppings(p2, List("Onion"))
  } yield p3

  val invalidPizzaOrder = for {
    p <- PizzaService.startOrder("Standard")
    p2 <- PizzaService.chooseBase(p, "Pan")
    p3 <- PizzaService.addToppings(p2, List("Cheese", ""))
  } yield p3

  Seq(pizzaOrder, invalidPizzaOrder).foreach(order => PizzaServiceInterpreter.apply(order) match {
    case \/-(pizza) => println("Your Pizza order is Valid")
    case -\/(err) => err.foreach(println)
  })

}
