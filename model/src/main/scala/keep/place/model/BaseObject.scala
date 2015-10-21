package keep.place.model

trait BaseObject[PK] {
  val id: Option[PK]
}
