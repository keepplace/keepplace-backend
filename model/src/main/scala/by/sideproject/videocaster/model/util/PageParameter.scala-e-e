package by.sideproject.videocaster.model.util

case class PageParameter(offset: Int = 0, count: Int = 20){
  val offsetItems = offset * count
}

object PageParameter {
  object ParamNames{
    val Offset = "offset"
    val Count = "count"
  }
  val default = PageParameter(0, 20)
  val all = PageParameter(0, Int.MaxValue)
  val first = PageParameter(0, 1)
}
