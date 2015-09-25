package geolocation

import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action._
import com.xebialabs.restito.semantics.Condition._
import com.xebialabs.restito.server.StubServer
import org.scalatest.{Matchers, BeforeAndAfterEach, FlatSpec}


class LocationClientSpec extends FlatSpec with Matchers with BeforeAndAfterEach {
  val exampleResponse = """{"results":[{"formatted_address" : "London SE15 6NP, UK", "geometry" : { "location" : { "lat" : 51.4857881, "lng" : -0.073819}}}], "status" : "OK"}"""
  var server: StubServer = _

  override def beforeEach {
    server = new StubServer().run()
  }

  override def afterEach {
    server.stop()
  }

  "the client" should "get location" in {
    val appId = "1a"

    whenHttp(server)
      .`match`(get("/maps/api/geocode/json"), parameter("address", "se156np,uk"), parameter("key", appId))
      .`then`(ok(), stringContent(exampleResponse))

    val location = new LocationClient("http", s"localhost:${server.getPort}", appId).getLocation("se156np", "uk")

    location.lat should be(51.4857881)
    location.lng should be(-0.073819)
  }
}
