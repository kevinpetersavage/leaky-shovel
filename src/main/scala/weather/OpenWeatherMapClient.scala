package weather

import spray.json._
import weather.OpenWeatherFormat._

import scala.io.Source

class OpenWeatherMapClient(scheme: String, host: String, appId: String) {
  def getTemperature(lat: Double, lon: Double): Double = {
    val url = s"$scheme://$host/data/2.5/weather?lat=$lat&lon=$lon&units=metric&APPID=$appId"

    val weather = Source
      .fromURL(url)
      .mkString
      .parseJson
      .convertTo[Weather]

    weather.main.temp
  }
}
