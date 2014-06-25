package gipsetter.amazon.api.operation

import gipsetter.amazon.api.stackable.CartRG
import gipsetter.amazon.api.{AmazonOp, AmazonRequest}

import scala.concurrent.Future

/**
 * @author alari (name.alari@gmail.com)
 * @since 24.10.13 13:20
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartCreate.html
 */
object CartCreate {

  def byItemAsins(items: (String, Int)*) = AmazonRequest(
    "CartCreate",
    items.view.zipWithIndex.foldLeft(Map.empty[String, String]) {
      case (m, ((asin, q), i)) =>
        m + (s"Item.$i.ASIN" -> asin) + (s"Item.$i.Quantity" -> q.toString)
    }
  )

  @deprecated("Use more granular api", "25.06.2014")
  def byAsins(items: (String, Int)*)(implicit op: AmazonOp): Future[Seq[CartRG]] =
    op(new CartRG)("CartCreate",
      items.view.zipWithIndex.foldLeft(Map.empty[String, String]) {
        case (m, ((asin, q), i)) =>
          m + (s"Item.$i.ASIN" -> asin) + (s"Item.$i.Quantity" -> q.toString)
      }
    )
}
