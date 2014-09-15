package carryx.amazon.api

import scala.concurrent.{ExecutionContext, Future}
import carryx.amazon.api.stackable.RG

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


  override def get(r: AmazonRequest): Future[AmazonResponse] = httpGetter(r.url(conf)).map(ab => AmazonResponse(ab._1, ab._2))
}

trait AmazonOp {
  implicit def context: ExecutionContext

  def get(r: AmazonRequest): Future[AmazonResponse]

  def read[T <: RG](reader: => T, r: AmazonRequest): Future[Seq[T]] = get(r).map(_.read(reader))
}