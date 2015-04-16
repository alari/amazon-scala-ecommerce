package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 16.04.15
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_OfferFull.html
 */
trait OfferFullRG extends RG with OffersRG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "OfferFull")
}