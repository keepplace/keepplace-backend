package by.sideproject.videocaster.model.util

//todo remove Iterator and use nonblocking api, e.g. play Iteratees
trait Page[+A] {
  def iterator: Iterator[A]
  def page: PageParameter
  def total: Int
  def items: Iterable[A]
  val offset = page.count
  lazy val prev = Option(page.offset - 1).filter(_ >= 0).map(p => page.copy(offset = p))
  lazy val next = Option(page.offset + 1).filter(_ => (offset + items.size) < total).map(p => page.copy(offset = p))
}
object Page {
  def apply[A](_iterator: Iterator[A], _page: PageParameter, _total: Int): Page[A] = new Page[A] {
    def iterator = _iterator
    lazy val _items = iterator.toVector
    def items = _items
    def total = _total
    def page = _page
  }
  def apply[A](_items: Iterable[A], _page: PageParameter, _total: Int): Page[A] = new Page[A] {
    def iterator = items.iterator
    def items = _items
    def total = _total
    def page = _page
  }
}
