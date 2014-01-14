package gipsetter.amazon.api.operation

import gipsetter.amazon.api.stackable.{BrowseNodeInfoRG, AmazonNode}
import gipsetter.amazon.api.AmazonOp

/**
 * @author alari (name.alari@gmail.com)
 * @since 14.10.13 12:57
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/BrowseNodeLookup.html
 */
object BrowseNodeLookup {

  def byId[T <: AmazonNode](builder: => T)(nodeId: Long)(implicit op: AmazonOp) =
    op(builder)("BrowseNodeLookup",
      Map("BrowseNodeId" -> nodeId.toString)
    )

  def infoById(nodeId: Long)(implicit op: AmazonOp) = byId(new AmazonNode with BrowseNodeInfoRG)(nodeId)

}
