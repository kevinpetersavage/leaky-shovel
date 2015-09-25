package weather

import spray.json.DefaultJsonProtocol

object OpenWeatherFormat extends DefaultJsonProtocol {
  implicit val a = jsonFormat1(Main)
  implicit val b = jsonFormat1(Weather)
}

case class Weather(main: Main)
case class Main(temp: Double)
