package utils.remote.amazon.operation

import play.api.test._
import utils.remote.amazon.stackable.{BrowseNodeInfoRG, AmazonNode}
import scala.concurrent.duration.FiniteDuration
import utils.remote.amazon.AmazonSpecification


/**
 * @author alari (name.alari@gmail.com)
 * @since 10.10.13 16:52
 */
class BrowseNodeLookupSpec extends AmazonSpecification {
  import main.Implicits.amazonOp


  "BrowseNodeLookup operation" should {

    "fetch leaf node" in new WithApplication {
      maybeUnavailable {

        BrowseNodeLookup.byId(new AmazonNode with BrowseNodeInfoRG)(2354568011l) must beLike[Seq[BrowseNodeInfoRG]] {
          case resp: Seq[BrowseNodeInfoRG] =>
            val n = resp.head
            n.ancestors.length should be_>=(2)
            n.children.length should be_==(0)
        }.await(2, FiniteDuration(2, "seconds"))

      }
    }

    "fetch branch node" in new WithApplication {
      maybeUnavailable {

        BrowseNodeLookup.infoById(13900851l) must beLike[Seq[BrowseNodeInfoRG]] {
          case resp: Seq[BrowseNodeInfoRG] =>
            val n = resp.head
            n.ancestors.length should be_==(1)
            n.children.length should be_>=(1)
        }.await(2, FiniteDuration(2, "seconds"))
      }
    }
  }
}
