package carryx.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 17:54
 */
class AmazonItem extends RG{
  def rgName = ""

  override def read(x: xml.Node, builder: =>this.type) =
    (x \ "Items" \ "Item").map{i =>
      val item = builder
      item.node = i
      item
    }.asInstanceOf[Seq[this.type]]

  lazy val asin = (node \ "ASIN").text
  lazy val parentAsin = textOpt("ParentASIN", node)
}
