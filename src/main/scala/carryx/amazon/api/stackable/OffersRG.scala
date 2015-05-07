package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 01.11.13 13:45
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_Offers.html
 */
trait OffersRG extends RG with OfferSummaryRG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "Offers")

  private lazy val ofn = (node \ "Offers").headOption

  lazy val offersTotal = ofn.map(int("TotalOffers", _)).getOrElse(0)
  lazy val offersTotalPages = ofn.map(int("TotalOfferPages", _)).getOrElse(0)
  lazy val offersMoreUrl = ofn.map(text("MoreOffersUrl", _))

  lazy val offers: Seq[OffersRG.Offer] =
    ofn.map(_ \ "Offer").getOrElse(Seq.empty).map {
      o =>
        for {
          listing <- (o \ "OfferListing").headOption
          attributes <- (o \ "OfferAttributes").headOption
          availabilityAttrs <- (listing \ "AvailabilityAttributes").headOption
        } yield OffersRG.Offer(
          OffersRG.Condition.values.find(_.toString == text("Condition", attributes)).getOrElse(OffersRG.Condition.Unknown),
          text("OfferListingId", listing),
          (o \ "Merchant").headOption.flatMap(textOpt("Name", _)),
          Price.build(listing \ "Price" head),
          (listing \ "SalePrice" headOption).map(n => Price.build(n)),
          (listing \ "AmountSaved" headOption).map(n => Price.build(n)),
          intOpt("AmountSaved", listing),
          text("Availability", listing),
          text("AvailabilityType", availabilityAttrs),
          int("MaximumHours", availabilityAttrs),
          int("MinimumHours", availabilityAttrs),

          int("IsEligibleForSuperSaverShipping", listing) > 0
        )
    }.collect {
      case Some(o) => o
    }

}

object OffersRG {

  case class Offer(
                    condition: Condition.Value,
                    listingId: String,
                    merchant: Option[String],
                    price: Price,
                    salePrice: Option[Price],
                    amountSaved: Option[Price],
                    percentageSaved: Option[Int],
                    availability: String,
                    availabilityType: String,
                    availableMaxHours: Int,
                    availableMinHours: Int,
                    isEligibleForSuperSaverShipping: Boolean
                    )

  object Condition extends Enumeration {
    val Used, New, Refurbished, Collectible, Unknown = Value
  }

}
