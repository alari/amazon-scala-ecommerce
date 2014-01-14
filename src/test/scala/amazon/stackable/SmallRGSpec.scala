package utils.remote.amazon.stackable

import utils.remote.amazon.operation.ItemLookup
import scala.concurrent.duration.FiniteDuration
import utils.remote.amazon.AmazonSpecification
import play.api.test.WithApplication

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 18:19
 */
class SmallRGSpec extends AmazonSpecification {
  import main.Implicits.amazonOp


  val itemId = "1476745374"
  val reader = {
    new AmazonItem with SmallRG
  }

  "small response group" should {
    "make correct rg name" in {
      reader.rgName must_== "Small"
    }

    "be fetched by id" in new WithApplication {
      maybeUnavailable {
        ItemLookup.byId(reader)(itemId) must beLike[Seq[AmazonItem with SmallRG]] {
          case i =>
            i.length must_== 1
            val item = i.head
            item.asin must_== itemId
            item.detailedPageUrl.length must be_>=(10)
        }.await(2, FiniteDuration(2, "seconds"))

        ItemLookup.byId(new AmazonItem with SmallRG)(itemId) must beLike[Seq[AmazonItem with SmallRG]] {
          case i =>
            i.length must_== 1
            val item = i.head
            item.asin must_== itemId
            item.detailedPageUrl.length must be_>=(10)
        }.await(2, FiniteDuration(2, "seconds"))
      }
    }
  }
}
