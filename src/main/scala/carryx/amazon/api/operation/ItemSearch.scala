package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

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

  def byKeywords(keywords: String, searchIndex: String, itemPage: Int = 1, addParams: Map[String, String] = Map("Availability" -> "Available")) = AmazonRequest(
    "ItemSearch",
    Map("Keywords" -> keywords,
      "SearchIndex" -> searchIndex,
      "ItemPage" -> itemPage.toString) ++ addParams
  )
}
