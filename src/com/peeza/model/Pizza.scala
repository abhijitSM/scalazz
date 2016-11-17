package com.peeza.model

sealed trait Category

case object Category {
  def fromName(str: String): Option[Category] = {
    str match {
      case "Standard" => Some(Standard)
      case "Deluxe" => Some(Deluxe)
      case _ => None
    }
  }
}

case object Standard extends Category

case object Deluxe extends Category

sealed trait Base

case object Base {
  def fromName(str: String): Option[Base] = {
    str match {
      case "ThinCrust" => Some(ThinCrust)
      case "Pan" => Some(Pan)
      case _ => None
    }
  }
}

case object ThinCrust extends Base

case object Pan extends Base

sealed trait Topping

case object Topping {
  def fromName(str: String): Option[Topping] = {
    str match {
      case "Onion" => Some(Onion)
      case "Mushroom" => Some(Mushroom)
      case "Pepper" => Some(Pepper)
      case "Chicken" => Some(Chicken)
      case "Cheese" => Some(Cheese)
      case _ => None
    }
  }
}

sealed trait StandardTopping extends Topping

case object Onion extends StandardTopping

case object Mushroom extends StandardTopping

case object Pepper extends StandardTopping

sealed trait DeluxeTopping extends StandardTopping

case object Chicken extends DeluxeTopping

case object Cheese extends DeluxeTopping


case class Pizza(category: Option[Category] = None, base: Option[Base] = None, toppings: List[Topping] = List.empty)
