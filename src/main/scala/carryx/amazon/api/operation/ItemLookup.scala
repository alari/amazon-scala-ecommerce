package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

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
}