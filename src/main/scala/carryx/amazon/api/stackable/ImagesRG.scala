package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 18:54
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_Images.html
 */
trait ImagesRG extends RG{
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "Images")

  lazy val images = ImagesRG.readImages(node)
  lazy val imageSets = node \ "ImageSets" \ "ImageSet" map {n =>
    (n \ "@Category").text.toString -> ImagesRG.readImages(n)
  } toMap
}

object ImagesRG {
  case class Image(url: String, height: Int, heightUnits: String, width: Int, widthUnits: String)

  private def readImages(x: xml.Node): Map[String,Image] = {
    x.child map {i =>
      if(i.label.endsWith("Image")) Some(
        i.label.substring(0, i.label.length - 5) -> Image(
          i \ "URL" text,
          (i \ "Height" text).toString.toInt,
          i \ "Height" \ "@Units" text,
          (i \ "Width" text).toString.toInt,
          i \ "Width" \ "@Units" text
        ))
      else None
    } filter(_.isDefined) map(_.get) toMap
  }
}
