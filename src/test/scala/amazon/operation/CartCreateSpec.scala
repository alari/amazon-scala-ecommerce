package utils.remote.amazon.operation

import play.api.test._
import utils.remote.amazon.stackable.CartRG
import scala.concurrent.duration.FiniteDuration
import utils.remote.amazon.AmazonSpecification


/**
 * @author alari (name.alari@gmail.com)
 * @since 24.10.13 13:59
 */
class CartCreateSpec extends AmazonSpecification {
  import main.Implicits.amazonOp


  "cart create operation" should {
    "create a cart" in new WithApplication {
      maybeUnavailable {
        CartCreate.byAsins("1476745374" -> 2) must beLike[Seq[CartRG]] {
          case cart =>
            cart.head.id.length must be_>=(1)
            cart.head.purchaseUrl.length must be_>=(10)
        }.await(2, FiniteDuration(2, "seconds"))
      }
    }
  }
}
