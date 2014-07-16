package gipsetter.amazon.api.stackable

/**
 * @author alari (name.alari@gmail.com)
 * @since 31.10.13 17:54
 */
abstract class RG {
  def rgName: String

  protected def buildName(superName: String, ownName: String) =
    if(superName.length > 0) superName + "," + ownName
    else ownName

  private[stackable] var node: xml.Node = null

  def debugNode = node

  def read(x: xml.Node, builder: => this.type): Seq[this.type]

  protected def text(of: String, n: xml.Node = node) = RG.text(of, n)

  protected def textOpt(of: String, n: xml.Node = node) = RG.textOpt(of, n)

  protected def int(of: String, n: xml.Node = node) = RG.int(of, n)

  protected def intOpt(of: String, n: xml.Node = node) = RG.intOpt(of, n)

}

object RG {
  def text(of: String, n: xml.Node) = if(n == null) "" else n \ of text

  def textOpt(of: String, n: xml.Node) = text(of, n) match {
    case "" => None
    case s => Some(s)
  }

  def int(of: String, n: xml.Node) = text(of, n) match {
    case "" => 0
    case nt => nt.toInt
  }

  def intOpt(of: String, n: xml.Node) = textOpt(of, n).map(_.toInt)
}