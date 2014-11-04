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
  def modify(cartId: String, hmac: String, items: (String, Int)*) = modifyRequest(cartId, hmac, items, None)

  /**
   * Move SavedForLater items to actual cart
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items pairs of (cart item id -> quantity)
   * @return
   */
  def moveToCart(cartId: String, hmac: String, items: (String, Int)*) = modifyRequest(cartId, hmac, items, Some("MoveToCart"))

  /**
   * Move actual cart item to SavedForLater list
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items pairs of (cart item id -> quantity)
   * @return
   */
  def saveForLater(cartId: String, hmac: String, items: (String, Int)*) = modifyRequest(cartId, hmac, items, Some("SaveForLater"))

  /**
   * Helper method for any CartModify request
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items pairs of (cart item id -> quantity)
   * @param action an action to add to all items
   * @return
   */
  def modifyRequest(cartId: String, hmac: String, items: Seq[(String, Int)], action: Option[String]) = AmazonRequest(
    "CartModify",

    items.view.zipWithIndex.foldLeft(Map(
      "CartId" -> cartId,
      "HMAC" -> hmac
    )) {
      case (m, ((cartItemId, q), i)) =>
        action.fold(m)(a => m + (s"Item.$i.Action" -> a)) + (s"Item.$i.CartItemId" -> cartItemId) + (s"Item.$i.Quantity" -> q.toString)
    }
  )
}
