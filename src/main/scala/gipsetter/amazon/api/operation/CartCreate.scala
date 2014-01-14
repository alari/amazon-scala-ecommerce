package gipsetter.amazon.api.operation

import scala.concurrent.Future
import gipsetter.amazon.api.AmazonOp
import gipsetter.amazon.api.stackable.CartRG

/**
 * @author alari (name.alari@gmail.com)
 * @since 24.10.13 13:20
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartCreate.html
 */
object CartCreate {
  def byAsins(items: (String, Int)*)(implicit op: AmazonOp): Future[Seq[CartRG]] =
    op(new CartRG)("CartCreate",
      items.view.zipWithIndex.foldLeft(Map.empty[String, String]) {
        case (m, ((asin, q), i)) =>
          m + (s"Item.$i.ASIN" -> asin) + (s"Item.$i.Quantity" -> q.toString)
      }
    )
}
