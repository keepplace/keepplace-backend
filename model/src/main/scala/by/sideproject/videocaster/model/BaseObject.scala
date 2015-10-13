package by.sideproject.videocaster.model

trait BaseObject[PK] {
  val id: Option[PK]
}
