package gipsetter.amazon.api.operation

import gipsetter.amazon.api.stackable.AmazonItem
import gipsetter.amazon.api.{AmazonRequest, AmazonOp}
import scala.concurrent.Future

/**
 * @author alari (name.alari@gmail.com)
 * @since 14.10.13 13:24
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/ItemSearch.html
 */
object ItemSearch {

  def byNodeId(nodeId: Long, searchIndex: String, itemPage: Int = 1, sort: Option[String] = None, addParams: Map[String, String] = Map("Availability" -> "Available")) = {
    val params = Map("BrowseNode" -> nodeId.toString,
      "SearchIndex" -> searchIndex,
      "ItemPage" -> itemPage.toString) ++ addParams

    AmazonRequest(
      "ItemSearch",
      sort.map(s => params + ("Sort" -> s)).getOrElse(params)
    )
  }

  @deprecated("Use more granular api", "25.06.2014")
  def byNode[T <: AmazonItem](builder: => T)(nodeId: Long, searchIndex: String, itemPage: Int = 1, sort: Option[String] = None, addParams: Map[String, String] = Map("Availability" -> "Available"))(implicit op: AmazonOp) = {
    val params = Map("BrowseNode" -> nodeId.toString,
      "SearchIndex" -> searchIndex,
      "ItemPage" -> itemPage.toString) ++ addParams

    op(builder)("ItemSearch",
      sort.map(s => params + ("Sort" -> s)).getOrElse(params)
    )
  }



  @deprecated("Use more granular api", "25.06.2014")
  def byKeywords[T <: AmazonItem](builder: => T)(keywords: String, itemPage: Int)(implicit op: AmazonOp) =
    byKeywordsParams(builder)(
      keywords, "All", itemPage,
      Map("Availability" -> "Available")
    )

  def byKeywords(keywords: String, searchIndex: String, itemPage: Int = 1, addParams: Map[String, String] = Map("Availability" -> "Available")) = AmazonRequest(
    "ItemSearch",
    Map("Keywords" -> keywords,
      "SearchIndex" -> searchIndex,
      "ItemPage" -> itemPage.toString) ++ addParams
  )

  @deprecated("Use more granular api", "25.06.2014")
  def byKeywordsParams[T <: AmazonItem](builder: => T)(keywords: String, searchIndex: String, itemPage: Int = 1, addParams: Map[String, String])(implicit op: AmazonOp) =
    op(builder)("ItemSearch",
      Map("Keywords" -> keywords,
        "SearchIndex" -> searchIndex,
        "ItemPage" -> itemPage.toString) ++ addParams
    )
}
