package utils.remote.amazon.operation

import play.api.test._

import scala.concurrent.duration.FiniteDuration
import utils.remote.amazon.stackable._
import utils.remote.amazon.AmazonSpecification

/**
 * @author alari (name.alari@gmail.com)
 * @since 10.10.13 16:52
 */
class ItemSearchSpec extends AmazonSpecification {
  import main.Implicits.amazonOp


  "ItemSearch operation" should {
    "fetch items in a node" in new WithApplication {
      maybeUnavailable {

        ItemSearch.byNode(new AmazonItem with MediumRG)(13900851, "Electronics") must beLike[Seq[AmazonItem with MediumRG]] {
          case resp: Seq[MediumRG] =>
            resp.length must be_>=(1)
        }.await(2, FiniteDuration(2, "seconds"))

      }
    }

    "search items by keywords" in new WithApplication {
      maybeUnavailable {
        ItemSearch.byKeywords(new AmazonItem with SmallRG)("iPad") must beLike[Seq[AmazonItem with SmallRG]] {
          case resp: Seq[SmallRG] =>
            resp.length must be_>=(1)
        }.await(2, FiniteDuration(2, "seconds"))
      }
    }
  }
}
