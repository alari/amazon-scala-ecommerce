package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

/**
 * @author alari (name.alari@gmail.com)
 * @since 14.10.13 12:57
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/BrowseNodeLookup.html
 */
object BrowseNodeLookup {

  def byId(nodeId: Long) = AmazonRequest("BrowseNodeLookup",
    Map("BrowseNodeId" -> nodeId.toString)
  )

}
