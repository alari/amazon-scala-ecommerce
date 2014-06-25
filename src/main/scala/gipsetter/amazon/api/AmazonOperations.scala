package gipsetter.amazon.api

import scala.concurrent.{ExecutionContext, Future}
import gipsetter.amazon.api.stackable.RG

/**
 * @author alari (name.alari@gmail.com)
 * @since 11.10.13 13:09
 */
case class AmazonOperations(awsAccessKey: String,
                            awsSecretKey: String,
                            apiVersion: String,

                            associateTag: String,

                            service: String = "AWSECommerceService",

                            requestUri: String = "/onca/xml",
                            endPoint: String = "webservices.amazon.com")(httpGetter: String => Future[(Int, scala.xml.Elem)], implicit val context: ExecutionContext) extends AmazonOp {

  val conf = AmazonConfig(awsAccessKey, awsSecretKey, apiVersion, associateTag, service, requestUri, endPoint)

  val signer = AmazonSigner(awsAccessKey, awsSecretKey, apiVersion, requestUri, endPoint)

  @deprecated("Use more granular api", "25.06.2014")
  override def apply(operation: String, params: Map[String, String]) = {
    val signedParams = signer(params + ("Operation" -> operation) + ("Service" -> service) + ("AssociateTag" -> associateTag))
    val queryString = AmazonSigner.canonicalize(signedParams)

    httpGetter(s"http://$endPoint$requestUri?$queryString")
  }

  @deprecated("Use more granular api", "25.06.2014")
  override def apply[T <: RG](reader: => T)(operation: String, params: Map[String, String]): Future[Seq[T]] = {
    val r = reader

    apply(operation, params + ("ResponseGroup" -> r.rgName)).map {
      resp =>
        AmazonErrors.read(resp._1, resp._2).map(throw _).getOrElse(r.read(resp._2, reader.asInstanceOf[r.type]))
    }
  }

  override def get(r: AmazonRequest): Future[AmazonResponse] = httpGetter(r.url(conf)).map(ab => AmazonResponse(ab._1, ab._2))
}

trait AmazonOp {
  implicit def context: ExecutionContext

  @deprecated("Use more granular api", "25.06.2014")
  def apply(operation: String, params: Map[String, String]): Future[(Int, scala.xml.Elem)]

  @deprecated("Use more granular api", "25.06.2014")
  def apply[T <: RG](reader: => T)(operation: String, params: Map[String, String]): Future[Seq[T]]

  def get(r: AmazonRequest): Future[AmazonResponse]

  def read[T <: RG](reader: => T, r: AmazonRequest): Future[Seq[T]] = get(r).map(_.read(reader))
}