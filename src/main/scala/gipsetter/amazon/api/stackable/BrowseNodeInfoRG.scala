package gipsetter.amazon.api.stackable

import org.slf4j.LoggerFactory

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 19:14
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/RG_BrowseNodeInfo.html
 */
trait BrowseNodeInfoRG extends RG {
  self: AmazonNode =>

  abstract override def rgName = buildName(super.rgName, "BrowseNodeInfo")

  lazy val index = BrowseNodeInfoRG.getIndex(ancestors.headOption.map(_.name).getOrElse(name))

  lazy val name = (node \ "Name").text
  lazy val isCategoryRoot = (node \ "IsCategoryRoot").length > 0
  lazy val ancestors = BrowseNodeInfoRG.collectAncestors((node \ "Ancestors").headOption).reverse
  lazy val children = (node \ "Children" \ "BrowseNode").map(BrowseNodeInfoRG.readNode)
}

object BrowseNodeInfoRG {

  lazy val logger = LoggerFactory.getLogger(this.getClass)

  val possibleIndexes = Set(
    "All", "Apparel", "Appliances", "ArtsAndCrafts", "Automotive", "Baby", "Beauty", "Blended", "Books", "Classical", "Collectibles", "DVD", "DigitalMusic", "Electronics", "GiftCards", "GourmetFood", "Grocery", "HealthPersonalCare", "HomeGarden", "Industrial", "Jewelry", "KindleStore", "Kitchen", "LawnAndGarden", "Marketplace", "MP3Downloads", "Magazines", "Miscellaneous", "Music", "MusicTracks", "MusicalInstruments", "MobileApps", "OfficeProducts", "OutdoorLiving", "PCHardware", "PetSupplies", "Photo", "Shoes", "Software", "SportingGoods", "Tools", "Toys", "UnboxVideo", "VHS", "Video", "VideoGames", "Watches", "Wireless", "WirelessAccessories"
  )
  val nodeIndexes = Map(
    "MoviesAndTV" -> "Video",
    "ClothingAndAccessories" -> "Apparel",
    "CellPhonesAndAccessories" -> "Wireless",
    "HomeAndKitchen" -> "Kitchen",
    "SportsAndOutdoors" -> "SportingGoods",
    "HealthAndPersonalCare" -> "HealthPersonalCare",
    "ArtsCraftsAndSewing" -> "ArtsAndCrafts",
    "CollectiblesAndFineArt" -> "Collectibles",
    "GroceryAndGourmetFood" -> "GourmetFood"
  )

  def getIndex(name: String): String = {
    val i = name.replace(" ", "").replace(",", "").replace("&", "And")
    if (possibleIndexes.contains(i)) i
    else if (nodeIndexes.contains(i)) nodeIndexes(i)
    else {
      logger.error(s"Wrong index: $i")
      "Apparel"
    }
  }

  case class Node(id: Long, name: String, isCategoryRoot: Boolean)

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
    Node((x \ "BrowseNodeId").text.toLong, (x \ "Name").text, (x \ "IsCategoryRoot").length > 0)
}
