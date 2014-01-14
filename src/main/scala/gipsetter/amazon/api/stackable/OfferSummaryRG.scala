package gipsetter.amazon.api.stackable


/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 19:00
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_OfferSummary.html
 */
trait OfferSummaryRG extends RG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "OfferSummary")

  lazy val offerNew = OfferSummaryRG.readSummary(node, OfferSummaryRG.KindNew)
  lazy val offerUsed = OfferSummaryRG.readSummary(node, OfferSummaryRG.KindUsed)
  lazy val offerCollectible = OfferSummaryRG.readSummary(node, OfferSummaryRG.KindCollectible)
  lazy val offerRefurbished = OfferSummaryRG.readSummary(node, OfferSummaryRG.KindRefurbished)
}

object OfferSummaryRG {
  val KindNew = "New"
  val KindUsed = "Used"
  val KindCollectible = "Collectible"
  val KindRefurbished = "Refurbished"

  val OfferSummary = "OfferSummary"

  case class Summary(quantity: Int, lowestPrice: Price)

  def readSummary(node: xml.Node, kind: String) = {
    val x = node \ OfferSummary

    val totalField = s"Total$kind"
    val priceField = s"Lowest${kind}Price"

    val total = (x \ totalField).text match {
      case "" => 0
      case s => s.toInt
    }
    if (total == 0) None
    else (x \ priceField headOption) map {
      p =>
        Summary(total, Price.build(p))
    }
  }
}