package nest

import akka.actor.Actor
import com.firebase.client.Firebase.AuthResultHandler
import com.firebase.client._
import database.ReadingLogger
import geolocation.LocationClient
import weather.OpenWeatherMapClient

class NestActor(nestToken: String, nestParser: NestParser, weather: OpenWeatherMapClient, locator: LocationClient,
                 readingLogger: ReadingLogger) extends Actor {

  val fb = new Firebase("https://developer-api.nest.com")

  fb.authWithCustomToken(nestToken, new AuthResultHandler {
    override def onAuthenticated(authData: AuthData): Unit = {
      println("fb auth success: " + authData)

      // when we've successfully authed, add a change listener to the whole tree
      fb.addValueEventListener(new ValueEventListener {
        def onDataChange(snapshot: DataSnapshot) {
          // when data changes we send our receive block an update
          self ! snapshot
        }

        def onCancelled(err: FirebaseError) {
          // on an err we should just bail out
          self ! err
        }
      })
    }

    override def onAuthenticationError(e: FirebaseError): Unit =  println("fb auth error: " + e.getMessage)
  })

  override def receive: Receive = {
    case s: DataSnapshot => {
      try {
        val nestReading = nestParser.parse(s)
        val location = locator.getLocation(nestReading.postcode, nestReading.country)
        val temperature = weather.getTemperature(location.lat, location.lng)

        readingLogger.log(nestReading, temperature)
      } catch {
        case e : Exception => println("exception parsing nest data", e);
      }
    }
    case e: FirebaseError => println(s"got firebase error: ${e.getMessage}")
  }
}
