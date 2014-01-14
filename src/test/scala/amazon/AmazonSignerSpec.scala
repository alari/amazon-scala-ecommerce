package utils.remote.amazon

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import play.api.Logger

/**
 * @author alari (name.alari@gmail.com)
 * @since 10.10.13 16:52
 */
class AmazonSignerSpec extends PlaySpecification{
  "AmazonSigner" should {
      "provide correct digest" in {
        val stringToSign = "GET\nwebservices.amazon.com\n/onca/xml\nAWSAccessKeyId=AKIAIOSFODNN7EXAMPLE&ItemId=0679722769&Operation=ItemLookup&ResponseGroup=ItemAttributes%2COffers%2CImages%2CReviews&Service=AWSECommerceService&Timestamp=2009-01-01T12%3A00%3A00Z&Version=2009-01-06"

        AmazonSigner.digest(stringToSign, "1234567890") must_== "M/y0+EAFFGaUAp4bWv/WEuXYah99pVsxvqtAuC8YN7I="
      }

    "sign the request with correct params" in {
      val signer = AmazonSigner("AKIAIOSFODNN7EXAMPLE", "1234567890", "2009-01-06", requestUri = "/onca/xml", endPoint = "webservices.amazon.com")
      val params = Map(
        "Service" -> "AWSECommerceService",
        "Operation" -> "ItemLookup",
        "ItemId" -> "0679722769",
        "ResponseGroup" -> "ItemAttributes,Offers,Images,Reviews",
        "Timestamp" -> "2009-01-01T12:00:00Z"
      )

      signer(params, "GET").get("Signature") must beSome("M/y0+EAFFGaUAp4bWv/WEuXYah99pVsxvqtAuC8YN7I=")
    }
  }
}
