package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

/**
 * @author alari (name.alari@gmail.com)
 * @since 15.09.14
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartGet.html
 */
object CartModify {
  /**
   * Modify the quantity of items inside a cart
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items pairs of (cart item id -> quantity)
   * @return
   */
  def modify(cartId: String, hmac: String, items: (String, Int)*) = AmazonRequest(
     "CartModify",

      items.view.zipWithIndex.foldLeft(Map(
        "CartId" -> cartId,
        "HMAC" -> hmac
      )) {
    case (m, ((cartItemId, q), i)) =>
      m + (s"Item.$i.CartItemId" -> cartItemId) + (s"Item.$i.Quantity" -> q.toString)
  }
  )
}
