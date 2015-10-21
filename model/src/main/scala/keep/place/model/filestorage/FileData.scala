package keep.place.model.filestorage

case class FileData(meta: FileMeta, data: Option[Array[Byte]])
