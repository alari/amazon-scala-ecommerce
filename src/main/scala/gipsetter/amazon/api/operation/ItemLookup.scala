package gipsetter.amazon.api.operation

import scala.concurrent.{ExecutionContext, Future}
import gipsetter.amazon.api.stackable.AmazonItem
import gipsetter.amazon.api.{AmazonRequest, AmazonOp}

/**
 * @author alari (name.alari@gmail.com)
 * @since 14.10.13 13:38
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/ItemLookup.html
 */
object ItemLookup {

  def byId(itemId: String) = AmazonRequest(
    "ItemLookup",
    Map("ItemId" -> itemId)
  )

  def byIds(itemIds: Seq[String]) = {
    require(itemIds.size < 11, "Please split your request into 10-items portions")
    require(itemIds.nonEmpty, "Please provide at least a single item id")
    AmazonRequest(
      "ItemLookup",
      Map("ItemId" -> itemIds.mkString(","))
    )
  }

  @deprecated("Use more granular api", "25.06.2014")
  def byId[T <: AmazonItem](reader: => T)(itemId: String)(implicit op: AmazonOp) =
    op(reader)("ItemLookup", Map("ItemId" -> itemId))

  @deprecated("Use more granular api", "25.06.2014")
  def byIds[T <: AmazonItem](reader: => T)(itemIds: Seq[String])(implicit op: AmazonOp): Future[Seq[T]] = {
    import op.context
    (Future sequence itemIds.grouped(10).map(ids => byId(reader)(ids.mkString(","))).toSeq).map {
      _.reduce(_ ++ _)
    }
  }
}