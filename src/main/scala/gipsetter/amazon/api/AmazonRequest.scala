package gipsetter.amazon.api

import gipsetter.amazon.api.stackable.RG

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

  def url(conf: AmazonConfig, rg: RG) = url(conf, rg.rgName)

  def url(conf: AmazonConfig, rgName: String) = withGroupName(rgName).url(conf)

  def withGroup(rg: RG) = withGroupName(rg.rgName)

  def withGroupName(rgName: String) = copy(params = params + ("ResponseGroup" -> rgName))
}