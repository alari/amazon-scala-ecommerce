package gipsetter.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 19:11
 */
class AmazonNode extends RG{
  def rgName = ""

  override def read(x: xml.Node, builder: =>this.type) =
    (x \ "BrowseNodes" \ "BrowseNode").map{i =>
      val item = builder
      item.node = i
      item
    }.asInstanceOf[Seq[this.type]]

  lazy val id = (node \ "BrowseNodeId").text.toLong
}
