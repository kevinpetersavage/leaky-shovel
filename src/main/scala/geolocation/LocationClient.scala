package geolocation

import spray.json._
import GoogleLocationFormat._

import scala.io.Source

class LocationClient(scheme: String, host: String, appId: String) {
  def getLocation(postCode: String, country: String) : Location = {
    val url = s"$scheme://$host/maps/api/geocode/json?address=$postCode,$country&key=$appId"
    val location = Source
      .fromURL(url)
      .mkString
      .parseJson
      .convertTo[GoogleLocation]

    location.results.head.geometry.location
  }
}
