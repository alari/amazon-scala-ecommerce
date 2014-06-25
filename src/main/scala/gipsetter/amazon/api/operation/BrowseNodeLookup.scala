package gipsetter.amazon.api.operation

import gipsetter.amazon.api.stackable.{BrowseNodeInfoRG, AmazonNode}
import gipsetter.amazon.api.{AmazonRequest, AmazonOp}

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

  @deprecated("Use more granular api", "25.06.2014")
  def byId[T <: AmazonNode](builder: => T)(nodeId: Long)(implicit op: AmazonOp) =
    op(builder)("BrowseNodeLookup",
      Map("BrowseNodeId" -> nodeId.toString)
    )

  @deprecated("Use more granular api", "25.06.2014")
  def infoById(nodeId: Long)(implicit op: AmazonOp) = byId(new AmazonNode with BrowseNodeInfoRG)(nodeId)

}
