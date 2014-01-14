package gipsetter.amazon.api.operation

import scala.concurrent.{ExecutionContext, Future}
import gipsetter.amazon.api.stackable.AmazonItem
import gipsetter.amazon.api.AmazonOp

/**
 * @author alari (name.alari@gmail.com)
 * @since 14.10.13 13:38
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/ItemLookup.html
 */
object ItemLookup {

  def byId[T <: AmazonItem](reader: => T)(itemId: String)(implicit op: AmazonOp) =
    op(reader)("ItemLookup", Map("ItemId" -> itemId))

  def byIds[T <: AmazonItem](reader: => T)(itemIds: Seq[String])(implicit op: AmazonOp): Future[Seq[T]] = {
    import op.context
    (Future sequence itemIds.grouped(10).map(ids => byId(reader)(ids.mkString(","))).toSeq).map {
      _.reduce(_ ++ _)
    }
  }
}
