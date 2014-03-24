package gipsetter.amazon.api

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 18:02
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/ErrorMessages.html
 */
case class AmazonErrors(status: Int, errors: Seq[AmazonErrors.Err]) extends Throwable {
  override def getMessage: String = errors.map(_.message).mkString("\n")
}

object AmazonErrors {
  case class Err(code: String, message: String, items: Option[Seq[String]])

  lazy val InvalidQuantityR = "You have exceeded the maximum quantity allowed for the following item\\(s\\): ([^\\.]+)\\.".r
  lazy val ItemAlreadyInCartR = "The item you specified, ([^,]+), is already in your cart\\.".r
  lazy val ItemNotEligibleForCartR = "The item you specified, ([^,]+), is not eligible to be added to the cart\\. Check the item's availability to make sure it is available\\.".r
  lazy val NoSimilaritiesR = "There are no similar items for this ASIN\\(s\\): ([^\\.]+)\\.".r

  implicit def toLst(l: String): Seq[String] = l.split(',').map(_.trim())

  def readError(code: String, message: String): Err = Err(code, message, (code, message) match {
    case ("AWS.ECommerceService.InvalidQuantity", InvalidQuantityR(i)) => Some(toLst(i))
    case ("AWS.ECommerceService.ItemAlreadyInCart", ItemAlreadyInCartR(i)) => Some(Seq(i))
    case ("AWS.ECommerceService.ItemNotEligibleForCart", ItemNotEligibleForCartR(i)) => Some(Seq(i))
    case ("AWS.ECommerceService.NoSimilarities", NoSimilaritiesR(asins)) => Some(toLst(asins))
    case _ => None
  })

  def read(status: Int, x: xml.Node) = {
    val errors = x \\ "Error"
    if (errors.length > 0) {
      Some(AmazonErrors(status, errors.map{err =>
        val code = (err \ "Code").text
        val message = (err \ "Message").text
        readError(code, message)}))
    } else None
  }
}