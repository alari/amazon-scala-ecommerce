package gipsetter.amazon.api

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 18:02
 */
case class AmazonErrors(status: Int, errors: Seq[AmazonErrors.Err]) extends Throwable {
  override def getMessage: String = errors.map(_.message).mkString("\n")
}

object AmazonErrors {
  case class Err(code: String, message: String)

  def read(status: Int, x: xml.Node) = {
    val errors = x \\ "Error"
    if(errors.length > 0) {
      Some(AmazonErrors(status, errors.map(err => Err(err \ "Code" text, err \ "Message" text))))
    } else None
  }
}