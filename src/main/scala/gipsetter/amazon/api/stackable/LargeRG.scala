package gipsetter.amazon.api.stackable

/**
 * @author alari
 * @since 12/2/13 1:28 PM
 */
trait LargeRG extends RG with BrowseNodesRG with MediumRG {
  self: AmazonItem =>
  abstract override def rgName = buildName(super.rgName, "Large")
}
