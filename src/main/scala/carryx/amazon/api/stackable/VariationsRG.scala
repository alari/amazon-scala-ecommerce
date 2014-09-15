package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 04.11.13 12:47
 */
trait VariationsRG extends RG with VariationSummaryRG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "Variations")

  lazy val variations = VariationsRG.parseVariations(node \ "Variations" \ "Item")
}

object VariationsRG {
  type T = AmazonItem with SmallRG with ImagesRG with OffersRG with ItemAttributesRG with VariationSummaryRG

  def parseVariations(x: xml.NodeSeq): Seq[T] =
    x map {i =>
    val item = builder
    item.node = i
    item
  }

  def builder: T = new AmazonItem with SmallRG with ImagesRG with OffersRG with ItemAttributesRG with VariationSummaryRG
}