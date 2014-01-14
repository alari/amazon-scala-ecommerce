package gipsetter.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 01.11.13 17:39
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_ItemAttributes.html
 */
trait ItemAttributesRG extends RG {
  self: AmazonItem =>
  abstract override def rgName = buildName(super.rgName, "ItemAttributes")

  private lazy val itemAttrs = node \ "ItemAttributes"

  lazy val dimensions = (itemAttrs \ "ItemDimensions" headOption) map ItemAttributesRG.readDimensions

  lazy val price = (itemAttrs \ "ListPrice" headOption).map(Price.build)

  lazy val packageDimensions = (itemAttrs \ "PackageDimensions" headOption) map ItemAttributesRG.readDimensions

  lazy val features = (itemAttrs \ "Feature") map (_.text)
}

object ItemAttributesRG {

  import RG._

  case class Dimensions(
                         height: Option[Int],
                         heightUnits: Option[String],

                         width: Option[Int],
                         widthUnits: Option[String],

                         length: Option[Int],
                         lengthUnits: Option[String],

                         weight: Option[Int],
                         weightUnits: Option[String]
                         )

  def readDimensions(dim: xml.Node): Dimensions = ItemAttributesRG.Dimensions(
    intOpt("Height", dim),
    Some(dim \ "Height" \ "@Units" text),

    intOpt("Width", dim),
    Some(dim \ "Width" \ "@Units" text),

    intOpt("Length", dim),
    Some(dim \ "Length" \ "@Units" text),

    intOpt("Weight", dim),
    Some(dim \ "Weight" \ "@Units" text)
  )
}
