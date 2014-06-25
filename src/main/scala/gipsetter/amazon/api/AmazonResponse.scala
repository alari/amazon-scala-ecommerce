package gipsetter.amazon.api

import gipsetter.amazon.api.stackable.RG

/**
 * @author alari
 * @since 6/25/14
 */
case class AmazonResponse(status: Int, body: scala.xml.Elem) {
  def read[T <: RG](reader: => T): Seq[T] = {
    val r = reader

    AmazonErrors.read(status, body).map(throw _).getOrElse(r.read(body, reader.asInstanceOf[r.type]))
  }
}