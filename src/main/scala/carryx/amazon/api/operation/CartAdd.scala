package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

/**
 * @author alari (name.alari@gmail.com)
 * @since 15.09.14
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartGet.html
 */
object CartAdd {
  /**
   * Add items to a cart
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items pairs of (asin -> quantity)
   * @return
   */
  def byAsins(cartId: String, hmac: String, items: (String, Int)*) = AmazonRequest(
     "CartAdd",
      items.view.zipWithIndex.foldLeft(Map(
        "CartId" -> cartId,
        "HMAC" -> hmac
      )) {
    case (m, ((asin, q), i)) =>
      m + (s"Item.$i.ASIN" -> asin) + (s"Item.$i.Quantity" -> q.toString)
  }
  )


  /**
   * Add items to a cart
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items pairs of (offer id -> quantity)
   * @return
   */
  def byOfferListingIds(cartId: String, hmac: String, items: (String, Int)*) = AmazonRequest(
     "CartAdd",
      items.view.zipWithIndex.foldLeft(Map(
        "CartId" -> cartId,
        "HMAC" -> hmac
      )) {
    case (m, ((offerListingId, q), i)) =>
      m + (s"Item.$i.OfferListingId" -> offerListingId) + (s"Item.$i.Quantity" -> q.toString)
  }
  )
}
