package gipsetter.amazon.api

/**
 * @author alari
 * @since 6/25/14
 */
case class AmazonRequest(operation: String, params: Map[String, String]) {
  def url(conf: AmazonConfig) = {
    val signedParams = conf.signer(params + ("Operation" -> operation) + ("Service" -> conf.service) + ("AssociateTag" -> conf.associateTag))
    val queryString = AmazonSigner.canonicalize(signedParams)

    s"http://${conf.endPoint}${conf.requestUri}?$queryString"
  }
}