package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 19:21
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_Cart.html
 */
class CartRG extends RG {
  def rgName = "Cart"

  override def read(x: xml.Node, builder: => this.type) = {
    val cart = builder
    cart.node = (x \ "Cart").head
    Seq[this.type](cart)
  }

  lazy val cartItems = node \ "CartItems" \ "CartItem" map {
    i =>
      CartRG.CartItem(
        text("CartItemId", i),
        text("ASIN", i),
        int("Quantity", i),
        text("Title", i),
        text("ProductGroup", i),
        Price.build(i \ "Price" head),
        Price.build(i \ "ItemTotal" head)
      )
  }

  lazy val hmac = text("HMAC")
  lazy val urlEncodedHmac = text("URLEncodedHMAC")

  lazy val id = text("CartId")
  lazy val purchaseUrl = text("PurchaseURL")

  lazy val subTotal = Price.build(node \ "SubTotal" head)
}

object CartRG {

  case class CartItem(
                       cartItemId: String,
                       asin: String,
                       quantity: Int,
                       title: String,
                       productGroup: String,
                       price: Price,
                       itemTotal: Price
                       )

}