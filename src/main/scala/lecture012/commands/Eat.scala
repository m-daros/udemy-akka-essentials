package lecture012.commands

object Food {

	val CHOCOLATE: String  = "chocolate"
	val VEGETABLES: String = "vegetables"
}

case class Eat ( food: String )