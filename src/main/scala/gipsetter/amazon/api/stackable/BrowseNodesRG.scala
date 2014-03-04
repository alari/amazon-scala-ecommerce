package gipsetter.amazon.api.stackable

/**
 * @author alari
 * @since 12/2/13 1:25 PM
 */
trait BrowseNodesRG extends RG {
  self: AmazonItem =>

  abstract override def rgName = buildName(super.rgName, "BrowseNodes")

  lazy val nodes = BrowseNodesRG.collectAncestors(node \ "BrowseNodes" headOption)
}

object BrowseNodesRG {

  case class Node(id: Long, name: String, ancestors: List[Node])

  private def collectAncestors(x: Option[scala.xml.Node]): List[Node] = x match {
      case Some(node) =>
        (node \ "BrowseNode").map(readNode).toList
      case None =>
        Nil
    }

  private def readNode(x: scala.xml.Node) =
    Node((x \ "BrowseNodeId").text.toLong, (x \ "Name").text, collectAncestors(x \ "Ancestors" headOption))
}