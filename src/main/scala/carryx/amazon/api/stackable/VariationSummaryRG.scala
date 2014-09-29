package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 04.11.13 12:47
 */
trait VariationSummaryRG extends RG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "VariationSummary")

  lazy val variationsLowestPrice = (node \ "VariationSummary" headOption) map (_ \ "LowestPrice" head) map {p => Price.build(p)}
  lazy val variationsHighestPrice = (node \ "VariationSummary" headOption) map (_ \ "HighestPrice" head) map {p => Price.build(p)}
}
