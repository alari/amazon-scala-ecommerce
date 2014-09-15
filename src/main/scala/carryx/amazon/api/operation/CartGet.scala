package carryx.amazon.api.operation

import carryx.amazon.api.AmazonRequest

/**
 * @author alari (name.alari@gmail.com)
 * @since 15.09.14
 *
 * @see http://docs.aws.amazon.com/AWSECommerceService/latest/DG/CartGet.html
 */
object CartGet {
  def get(id: String, hmac: String) = AmazonRequest(
     "CartGet",
    Map(
      "CartId" -> id,
      "HMAC" -> hmac
    )
  )
}
