package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 17:56
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_Medium.html
 */
trait MediumRG extends RG with SmallRG with ImagesRG with OfferSummaryRG with ItemAttributesRG {
  self: AmazonItem =>
  abstract override def rgName = buildName(super.rgName, "Medium")
}
