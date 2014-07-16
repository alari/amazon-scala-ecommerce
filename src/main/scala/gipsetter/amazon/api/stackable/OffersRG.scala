package gipsetter.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 01.11.13 13:45
 *
 * @see
 */
trait OffersRG extends RG with OfferSummaryRG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "Offers")

  private lazy val ofn = (node \ "Offers" headOption).orNull

  lazy val offersTotal = int("TotalOffers", ofn)
  lazy val offersTotalPages = int("TotalOfferPages", ofn)
  lazy val offersMoreUrl = text("MoreOffersUrl", ofn)
  lazy val offers = ofn \ "Offer" map {
    o =>
      val listing = o \ "OfferListing" head
      val attributes = o \ "OfferAttributes" head
      val availabilityAttrs = listing \ "AvailabilityAttributes" head

      OffersRG.Offer(
        text("Condition", attributes),
        text("OfferListingId", listing),
        Price.build(listing \ "Price" head),
        text("Availability", listing),
        text("AvailabilityType", availabilityAttrs),
        int("MaximumHours", availabilityAttrs),
        int("MinimumHours", availabilityAttrs),

        int("IsEligibleForSuperSaverShipping", listing) > 0
      )
  }
}

object OffersRG {

  case class Offer(
                    condition: String,
                    listingId: String,
                    price: Price,
                    availability: String,
                    availabilityType: String,
                    availableMaxHours: Int,
                    availableMinHours: Int,
                    isEligibleForSuperSaverShipping: Boolean
                    )


}
