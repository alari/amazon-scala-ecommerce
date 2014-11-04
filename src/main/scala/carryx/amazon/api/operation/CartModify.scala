package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

/**
 * @author alari (name.alari@gmail.com)
 * @since 15.09.14
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartModify.html
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
  def modify(cartId: String, hmac: String, items: (String, Int)*) = modifyRequest(cartId, hmac, items.map(i => i._1 -> Right(i._2)))

  /**
   * Move SavedForLater items to actual cart
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items cart item ids
   * @return
   */
  def moveToCart(cartId: String, hmac: String, items: String*) = modifyRequest(cartId, hmac, items.map(_ -> Left("MoveToCart")))

  /**
   * Move actual cart item to SavedForLater list
   *
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items cart item ids
   * @return
   */
  def saveForLater(cartId: String, hmac: String, items: String*) = modifyRequest(cartId, hmac, items.map(_ -> Left("SaveForLater")))

  /**
   * Helper method for any CartModify request
   * @param cartId cart id
   * @param hmac cart hmac
   * @param items pairs of (cart item id -> either Left(action) or Right(quantity))
   * @return
   */
  def modifyRequest(cartId: String, hmac: String, items: Seq[(String,Either[String,Int])]) = AmazonRequest(
    "CartModify",

    items.view.zipWithIndex.foldLeft(Map(
      "CartId" -> cartId,
      "HMAC" -> hmac
    )) {
      case (m, ((cartItemId, Left(action)), i)) =>
        m + (s"Item.$i.Action" -> action) + (s"Item.$i.CartItemId" -> cartItemId)

      case (m, ((cartItemId, Right(quantity)), i)) =>
        m + (s"Item.$i.Quantity" -> quantity.toString) + (s"Item.$i.CartItemId" -> cartItemId)
    }
  )
}
