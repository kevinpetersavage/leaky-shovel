package database

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import nest.NestReading

class ReadingLogger {
  val client = new AmazonDynamoDBClient(new ProfileCredentialsProvider())


  def log(reading: NestReading, externalTempC: Double) = {
    client.putItem("Reading",
      Map(
        "name" -> reading.name,
        "targetTempC" -> reading.targetTempC,
        "postcode" -> reading.postcode,
        "country" -> reading.country,
        "externalTempC" -> externalTempC
      )
    )
  }

}
