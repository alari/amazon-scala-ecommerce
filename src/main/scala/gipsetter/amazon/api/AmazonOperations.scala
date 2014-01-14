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

  val signer = AmazonSigner(awsAccessKey, awsSecretKey, apiVersion, requestUri, endPoint)

  override def apply(operation: String, params: Map[String, String]) = {
    val signedParams = signer(params + ("Operation" -> operation) + ("Service" -> service) + ("AssociateTag" -> associateTag))
    val queryString = AmazonSigner.canonicalize(signedParams)

    httpGetter(s"http://$endPoint$requestUri?$queryString")
  }

  override def apply[T <: RG](reader: => T)(operation: String, params: Map[String, String]): Future[Seq[T]] = {
    val r = reader

    apply(operation, params + ("ResponseGroup" -> r.rgName)).map {
      resp =>
        AmazonErrors.read(resp._1, resp._2).map(throw _).getOrElse(r.read(resp._2, reader.asInstanceOf[r.type]))
    }
  }
}

trait AmazonOp {
  implicit def context: ExecutionContext

  def apply(operation: String, params: Map[String, String]): Future[(Int, scala.xml.Elem)]

  def apply[T <: RG](reader: => T)(operation: String, params: Map[String, String]): Future[Seq[T]]
}