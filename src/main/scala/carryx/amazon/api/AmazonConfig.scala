package carryx.amazon.api

/**
 * @author alari
 * @since 6/25/14
 */
case class AmazonConfig(awsAccessKey: String,
                        awsSecretKey: String,
                        apiVersion: String,

                        associateTag: String,

                        service: String = "AWSECommerceService",

                        requestUri: String = "/onca/xml",
                        endPoint: String = "webservices.amazon.com") {
  lazy val signer = AmazonSigner(awsAccessKey, awsSecretKey, apiVersion, requestUri, endPoint)
}