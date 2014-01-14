package gipsetter.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 17:55
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_Small.html
 */
trait SmallRG extends RG{
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "Small")

  lazy val detailedPageUrl = (node \ "DetailPageURL").text
  lazy val links = (node \ "ItemLinks" \ "ItemLink").map {link => (link \ "Description").text -> (link \ "URL").text}.toMap
  lazy val attrs = (node \ "ItemAttributes").head.nonEmptyChildren.map(attr => attr.label -> attr.text).toMap
}