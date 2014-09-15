package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

/**
 * @author alari (name.alari@gmail.com)
 * @since 24.10.13 13:20
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartCreate.html
 */
object CartCreate {

  def byItemAsins(items: (String, Int)*) = AmazonRequest(
    "CartCreate",
    items.view.zipWithIndex.foldLeft(Map.empty[String, String]) {
      case (m, ((asin, q), i)) =>
        m + (s"Item.$i.ASIN" -> asin) + (s"Item.$i.Quantity" -> q.toString)
    }
  )

  def byOfferListingIds(listings: (String, Int)*) = AmazonRequest(
    "CartCreate",
    listings.view.zipWithIndex.foldLeft(Map.empty[String, String]) {
      case (m, ((listingId, q), i)) =>
        m + (s"Item.$i.OfferListingId" -> listingId) + (s"Item.$i.Quantity" -> q.toString)
    }
  )
}
