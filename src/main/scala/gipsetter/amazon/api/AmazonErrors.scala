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

  sealed trait Error

  case class RequestThrottled(id_ip: String) extends Error

  lazy val RequestThrottledR = "Request from ([^\\ ]*) is throttled\\.".r

  case class Unknown(code: String, message: String) extends Error

  case class ExactParameterRequirement(parameterName: String, maximumNumber: Int) extends Error

  lazy val ExactParameterRequirementR = "Your request contains too much data for ([^\\.*])\\. This parameter can have a maximum length of (\\d+)\\.".r

  case class ExceededMaximumParameterValues(parameterName: String, maximumNumber: Int) extends Error

  lazy val ExceededMaximumParameterValuesR = "Your request contains too many values for ([^\\.*])\\. This parameter can have a maximum of (\\d+) values\\.".r

  case class InsufficientParameterValues(parameterName: String, minimumNumber: Int) extends Error

  lazy val InsufficientParameterValuesR = "Your request contains too few values for ([^\\.*])\\. This parameter must have a minimum of (\\d+) values\\.".r

  case object InternalError extends Error

  case class InvalidAccount(registrationUrl: String) extends Error

  lazy val InvalidAccountR = "Your AccessKey Id is not registered for Product Advertising API\\. Please use the AccessKey Id obtained after registering at (.+)".r

  case class InvalidEnumeratedParameter(parameterName: String, values: Seq[String]) extends Error

  lazy val InvalidEnumeratedParameterR = "The value you specified for ([^ ]+) is invalid\\. Valid values include ([^\\.]+)\\.".r

  case class InvalidISO8601Time(parameterName: String) extends Error

  lazy val InvalidISO8601TimeR = "([^ ]+) has an invalid value\\. It must contain a valid ISO 8601 date and time\\.".r

  case class InvalidOperationForMarketplace(operationName: String) extends Error

  lazy val InvalidOperationForMarketplaceR = "This operation, ([^,]+), is not available for this locale\\.".r

  case class InvalidOperationParameter(operationValues: Seq[String]) extends Error

  lazy val InvalidOperationParameterR = "The Operation parameter is invalid\\. Please modify the Operation parameter and retry\\. Valid values for the Operation parameter include ([^\\.]+)\\.".r

  case class InvalidParameterCombination(parameterOne: String, parameterTwo: String) extends Error

  lazy val InvalidParameterCombinationR = "Your request contains an invalid parameter combination\\. ([^ ]+) and ([^ ]+) cannot appear in the same request\\.".r

  case class InvalidParameterValue(parameterValue: String, parameterName: String) extends Error

  lazy val InvalidParameterValueR = "(.+?) is not a valid value for ([^\\.])\\. Please change this value and retry your request\\.".r

  case class InvalidResponseGroup(operationName: String, responseGroups: Seq[String]) extends Error

  lazy val InvalidResponseGroupR = "Your ResponseGroup parameter is invalid\\. Valid response groups for ([^ ]+) requests include ([^\\.]+)\\.".r

  case class InvalidServiceParameter(services: Seq[String]) extends Error

  lazy val InvalidServiceParameterR = "The Service parameter is invalid\\. Please modify the Service parameter and retry. Valid values for the Service parameter include ([^\\.]+)\\.".r

  case object InvalidSubscriptionId extends Error

  case class MaximumParameterRequirement(maximumNumber: Int, parameters: Seq[String]) extends Error

  lazy val MaximumParameterRequirementR = "Your request should have at most ([\\d]+) of the following parameters: ([^\\.]+)\\.".r

  case class MinimumParameterRequirement(minimumNumber: Int, parameters: Seq[String]) extends Error

  lazy val MinimumParameterRequirementR = "Your request should have at least ([\\d]+) of the following parameters: ([^\\.]+)\\.".r

  case class MissingOperationParameter(operations: Seq[String]) extends Error

  lazy val MissingOperationParameterR = "Your request is missing the Operation parameter\\. Please add the Operation parameter to your request and retry\\. Valid values for the Operation parameter include ([^\\.]+)\\.".r

  case class MissingParameterCombination(parameter: String) extends Error

  lazy val MissingParameterCombinationR = "Your request is missing a required parameter combination\\. Required parameter combinations include ([^\\.]+)\\.".r

  case class MissingParameters(required: Seq[String]) extends Error

  lazy val MissingParametersR = "Your request is missing required parameters\\. Required parameters include ([^\\.]+)\\.".r

  case class MissingParameterValueCombination(parameterOne: String, restrictedValue: String, parameterTwo: String) extends Error

  lazy val MissingParameterValueCombinationR = "Your request is missing a required parameter combination\\. When ([^ ]+) equals ([^,]+), ([^ ]+) must be present\\.".r

  case class MissingServiceParameter(services: Seq[String]) extends Error

  lazy val MissingServiceParameterR = "Your request is missing the Service parameter\\. Please add the Service parameter to your request and retry\\. Valid values for the Service parameter include ([^\\.]+)\\.".r

  case class ParameterOutOfRange(parameter: String, lowerBound: String, upperBound: String) extends Error

  lazy val ParameterOutOfRangeR = "The value you specified for ([^ ]+) is invalid\\. Valid values must be between (\\d+) and (\\d+)\\.".r

  case class ParameterRepeatedInRequest(parameter: String) extends Error

  lazy val ParameterRepeatedInRequestR = "The parameter, ([^,]+), appeared more than once in your request\\.".r

  case class RestrictedParameterValueCombination(parameterOne: String, restrictedValue: String, parameterTwo: String) extends Error

  lazy val RestrictedParameterValueCombinationR = "Your request contains a restricted parameter combination\\. When ([^ ]+) equals ([^,]+), ([^ ]+) cannot be present\\.".r

  sealed trait ECommerceServiceError extends Error

  case class ExceededMaximumCartItems(maximumQuantity: Int) extends ECommerceServiceError

  lazy val ExceededMaximumCartItemsR = "You may not add more than (\\d+) items to the cart\\.".r

  case object InvalidCartId extends ECommerceServiceError

  case object InvalidHMAC extends ECommerceServiceError

  case class InvalidQuantity(itemIds: Seq[String]) extends ECommerceServiceError

  lazy val InvalidQuantityR = "You have exceeded the maximum quantity allowed for the following item\\(s\\): ([^\\.]+)\\.".r

  case class ItemAlreadyInCart(itemId: String) extends ECommerceServiceError

  lazy val ItemAlreadyInCartR = "The item you specified, ([^,]+), is already in your cart\\.".r

  case object ItemNotAccessible extends ECommerceServiceError

  case class ItemNotEligibleForCart(itemId: String) extends ECommerceServiceError

  lazy val ItemNotEligibleForCartR = "The item you specified, ([^,]+), is not eligible to be added to the cart\\. Check the item's availability to make sure it is available\\.".r

  case object NoExactMatches extends ECommerceServiceError

  case class NoSimilarities(itemIds: Seq[String]) extends ECommerceServiceError

  lazy val NoSimilaritiesR = "There are no similar items for this ASIN\\(s\\): ([^\\.]+)\\.".r

  implicit def toLst(l: String): Seq[String] = l.split(',').map(_.trim())

  def readError(code: String, message: String): Error = (code, message) match {

    case ("AWS.ECommerceService.NoExactMatches", _) => NoExactMatches
    case ("AWS.ECommerceService.ItemNotAccessible", _) => ItemNotAccessible
    case ("AWS.ECommerceService.InvalidCartId", _) => InvalidCartId
    case ("AWS.ECommerceService.InvalidHMAC", _) => InvalidHMAC
    case ("AWS.InvalidSubscriptionId", _) => InvalidSubscriptionId
    case ("AWS.InternalError", _) => InternalError
    case ("RequestThrottled", RequestThrottledR(id_ip)) => RequestThrottled(id_ip)
    case ("AWS.ExactParameterRequirement", ExactParameterRequirementR(p, mn)) => ExactParameterRequirement(p, mn.toInt)
    case ("AWS.ExceededMaximumParameterValues", ExceededMaximumParameterValuesR(p, mn)) => ExceededMaximumParameterValues(p, mn.toInt)
    case ("AWS.InsufficientParameterValues", InsufficientParameterValuesR(p, mv)) => InsufficientParameterValues(p, mv.toInt)
    case ("AWS.InvalidAccount", InvalidAccountR(r)) => InvalidAccount(r)
    case ("AWS.InvalidEnumeratedParameter", InvalidEnumeratedParameterR(pn, lst)) => InvalidEnumeratedParameter(pn, lst)
    case ("AWS.InvalidISO8601Time", InvalidISO8601TimeR(p)) => InvalidISO8601Time(p)
    case ("AWS.InvalidOperationForMarketplace", InvalidOperationForMarketplaceR(o)) => InvalidOperationForMarketplace(o)
    case ("AWS.InvalidOperationParameter", InvalidOperationParameterR(lst)) => InvalidOperationParameter(lst)
    case ("AWS.InvalidParameterCombination", InvalidParameterCombinationR(p1, p2)) => InvalidParameterCombination(p1, p2)
    case ("AWS.InvalidParameterValue", InvalidParameterValueR(v, p)) => InvalidParameterValue(v, p)
    case ("AWS.InvalidResponseGroup", InvalidResponseGroupR(om, lst)) => InvalidResponseGroup(om, lst)
    case ("AWS.InvalidServiceParameter", InvalidServiceParameterR(lst)) => InvalidServiceParameter(lst)
    case ("AWS.MaximumParameterRequirement", "Your request should have at most [Maximum Number] of the following parameters: [Parameter Names].") => ???
    case ("AWS.MinimumParameterRequirement", "Your request should have at least [Minimum Number] of the following parameters: [Parameter Names].") => ???
    case ("AWS.MissingOperationParameter", "Your request is missing the Operation parameter. Please add the Operation parameter to your request and retry. Valid values for the Operation parameter include [ValidOperationsList].") => ???
    case ("AWS.MissingParameterCombination", "Your request is missing a required parameter combination. Required parameter combinations include [Parameter One].") => ???
    case ("AWS.MissingParameters", "Your request is missing required parameters. Required parameters include [RequiredParameterList].") => ???
    case ("AWS.MissingParameterValueCombination", "Your request is missing a required parameter combination. When [Parameter One] equals [Restricted Value], [Parameter Two] must be present.") => ???
    case ("AWS.MissingServiceParameter", "Your request is missing the Service parameter. Please add the Service parameter to your request and retry. Valid values for the Service parameter include [ValidServicesList].") => ???
    case ("AWS.ParameterOutOfRange", "The value you specified for [ParameterName] is invalid. Valid values must be between [LowerBound] and [UpperBound].") => ???
    case ("AWS.ParameterRepeatedInRequest", "The parameter, [ParameterName], appeared more than once in your request.") => ???
    case ("AWS.RestrictedParameterValueCombination", "Your request contains a restricted parameter combination. When [Parameter One] equals [Restricted Value], [Parameter Two] cannot be present.") => ???
    case ("AWS.ECommerceService.ExceededMaximumCartItems", "You may not add more than [Maximum Item Quantity] items to the cart.") => ???
    case ("AWS.ECommerceService.InvalidQuantity", "You have exceeded the maximum quantity allowed for the following item(s): [ItemId].") => ???
    case ("AWS.ECommerceService.ItemAlreadyInCart", "The item you specified, [ItemID], is already in your cart.") => ???
    case ("AWS.ECommerceService.ItemNotEligibleForCart", "The item you specified, [ItemID], is not eligible to be added to the cart. Check the item's availability to make sure it is available.") => ???
    case ("AWS.ECommerceService.NoSimilarities", NoSimilaritiesR(asins)) => NoSimilarities(asins)

    case (c, m) => Unknown(c, m)
  }


  def read(status: Int, x: xml.Node) = {
    val errors = x \\ "Error"
    if (errors.length > 0) {
      Some(AmazonErrors(status, errors.map(err => Err(err \ "Code" text, err \ "Message" text))))
    } else None
  }
}