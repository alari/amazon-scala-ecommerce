package utils.remote.amazon

import play.api.test.PlaySpecification
import play.api.Logger
import mirari.wished.Unwished

/**
 * @author alari (name.alari@gmail.com)
 * @since 01.11.13 16:38
 */
trait AmazonSpecification extends PlaySpecification {
  def maybeUnavailable[T](t: => T) =
    try {
      t
    } catch {
      case e: Unwished[_] =>
        Logger.debug(e.toString)
    }
}
