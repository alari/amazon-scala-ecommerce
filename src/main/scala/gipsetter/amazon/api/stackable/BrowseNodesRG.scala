package gipsetter.amazon.api.stackable

/**
 * @author alari
 * @since 12/2/13 1:25 PM
 */
trait BrowseNodesRG extends RG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "BrowseNodes")

  lazy val nodes = (node \ "BrowseNodes" \ "BrowseNode").map(BrowseNodesRG.readNode)
}

object BrowseNodesRG {

  case class Node(id: Long, name: String)

  private def collectAncestors(x: Option[scala.xml.Node]): List[Node] =
    x match {
      case Some(node) =>
        (node \ "BrowseNode").headOption match {
          case Some(browseNode) =>
            readNode(browseNode) :: collectAncestors(browseNode \ "Ancestors" headOption)
          case None =>
            Nil
        }
      case None =>
        Nil
    }

  private def readNode(x: scala.xml.Node) =
    Node((x \ "BrowseNodeId").text.toLong, (x \ "Name").text)
}