package utils.remote.amazon.operation

import play.api.test._

import utils.remote.amazon.stackable._
import scala.concurrent.duration.FiniteDuration
import utils.remote.amazon.AmazonSpecification

/**
 * @author alari (name.alari@gmail.com)
 * @since 10.10.13 16:52
 */
class ItemLookupSpec extends AmazonSpecification {

  import main.Implicits.amazonOp

  "ItemLookup operation" should {
    val itemId = "B00746W9F2"

    "fetch item small by asin" in new WithApplication {
      maybeUnavailable {

        ItemLookup.byId(new AmazonItem with SmallRG)(itemId) must beLike[Seq[AmazonItem with SmallRG]] {
          case resp: Seq[SmallRG] =>
            resp.length must_== 1
            resp.head.asin must_== itemId
        }.await(2, FiniteDuration(2, "seconds"))
      }
    }

    "fetch item offer summary by asin" in new WithApplication {
      maybeUnavailable {

        ItemLookup.byId(new AmazonItem with OfferSummaryRG)(itemId) must beLike[Seq[AmazonItem with OfferSummaryRG]] {
          case resp: Seq[OfferSummaryRG] =>
            resp.length must_== 1
            resp.head.asin must_== itemId
            resp.head.offerNew must beSome
        }.await(2, FiniteDuration(2, "seconds"))
      }
    }

    "fetch item medium by asin" in new WithApplication {
      maybeUnavailable {

        ItemLookup.byId(new AmazonItem with MediumRG)(itemId) must beLike[Seq[AmazonItem with MediumRG]] {
          case resp: Seq[MediumRG] =>
            resp.length must_== 1
            resp.head.offerNew must beSome
            resp.head.asin must_== itemId
            resp.head.images.contains("Medium") must beTrue

        }.await(2, FiniteDuration(2, "seconds"))
      }
    }
  }
}
