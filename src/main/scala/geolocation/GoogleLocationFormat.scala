package geolocation

import spray.json.DefaultJsonProtocol

object GoogleLocationFormat extends DefaultJsonProtocol {

  implicit val d = jsonFormat2(Location)
  implicit val c = jsonFormat1(Geometry)
  implicit val b = jsonFormat1(Result)
  implicit val a = jsonFormat1(GoogleLocation)
}

case class GoogleLocation(results: List[Result])
case class Result(geometry: Geometry)
case class Geometry(location: Location)
case class Location(lat: Double, lng: Double)