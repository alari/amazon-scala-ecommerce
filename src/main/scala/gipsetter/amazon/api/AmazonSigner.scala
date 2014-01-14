package gipsetter.amazon.api

import java.net.URLEncoder
import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac
import org.joda.time.DateTime
import scala.collection.immutable.TreeMap
import org.apache.commons.codec.binary.Base64


/**
 * @author alari (name.alari@gmail.com)
 * @since 10.10.13 16:39
 */
case class AmazonSigner(
                       awsAccessKey: String,
                       awsSecretKey: String,
                       apiVersion: String,

                       requestUri: String = "/onca/xml",
                       endPoint: String = "webservices.amazon.com"
                       ) {

  /**
   * Takes an ordered map of parameters, signs it and returns the map
   * of parameters with the signature appended.
   */
  def apply(params: Map[String, String], requestMethod: String="GET"): TreeMap[String, String] = {
    val sortedParams = sortParams(params)

    val stringToSign = List(
      requestMethod,
      endPoint,
      requestUri,
      AmazonSigner.canonicalize(sortedParams)
    ).mkString("\n")

    sortedParams + ("Signature" -> AmazonSigner.digest(stringToSign, awsSecretKey)) // Return TreeMap with Signature on the end
  }

  def sortParams(params: Map[String, String]) = TreeMap(
    "AWSAccessKeyId" -> awsAccessKey,
    "Version" -> apiVersion,
    "Timestamp" -> DateTime.now().toString("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'")
  ) ++ params
}

object AmazonSigner {

  val Utf8Charset = "UTF-8"
  val HmacSha256Algorithm = "HmacSHA256"

  /**
   * Returns a canonicalized, escaped string of &key=value pairs from an ordered map of parameters
   */
  def canonicalize(params: TreeMap[String, String]): String =
    params.map(
      param => escape(param._1) + "=" + escape(param._2)
    ).mkString("&")

  /**
   * Returns the digest for a given string
   */
  def digest(x: String, awsSecretKey: String) = {
      val mac = Mac.getInstance(HmacSha256Algorithm)
      val secretKeySpec = new SecretKeySpec(awsSecretKey.getBytes(Utf8Charset), HmacSha256Algorithm)

      mac.init(secretKeySpec)

      val data = x.getBytes(Utf8Charset)
      val rawHmac = mac.doFinal(data)

      val encoder = new Base64()
      new String(encoder.encode(rawHmac))
  }

  /**
   * Returns an escaped string
   */
  protected def escape(s: String) =
      URLEncoder.encode(s, Utf8Charset).replace("+", "%20").replace("*", "%2A").replace("~", "%7E")

}