package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 01.11.13 17:57
 */
case class Price(amount: Option[Int], currency: Option[String], formatted: String)

object Price{
  val Amount = "Amount"
  val CurrencyCode = "CurrencyCode"
  val FormattedPrice = "FormattedPrice"

  def build(price: xml.Node): Price = {
    val a = price \ Amount text
    val c = price \ CurrencyCode text
    val f = price \ FormattedPrice text

    val amount = if (a.length > 0) Some(a.toInt) else None
    val currency = if (c.length > 0) Some(c) else None
    Price(amount, currency, f)
  }
}
