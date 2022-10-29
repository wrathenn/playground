import scala.annotation.tailrec

trait DesObject[T] {
  def halved(v: T): (T, T)
  def combined(a: T, b: T): T

  def applyTable(v: T, table: Seq[Int]): T
  def applySTable(v: T, sTable: Seq[Byte], offset: Int): T
  def shiftedLeft(v: T, i: Int): T
  def xored(a: T, b: T): T

  def expanded(v: T): T
  def truncated(v: T): T
  def toModel(data: Array[Byte]): T
  def fromModel(v: T): Array[Byte]
}

object DesFInstances {
  implicit val desByteArrayInstance: DesObject[Array[Byte]] = new DesObject[Array[Byte]] {
    override def halved(v: Array[Byte]): (Array[Byte], Array[Byte]) =
      (v.slice(0, v.length / 2), v.slice(v.length / 2, v.length))

    override def combined(a: Array[Byte], b: Array[Byte]): Array[Byte] =
      a ++ b

    override def applyTable(v: Array[Byte], table: Seq[Int]): Array[Byte] =
      table.map(i => v(i - 1)).toArray

    override def applySTable(v: Array[Byte], sTable: Seq[Byte], offset: Int): Array[Byte] = {
      val address = v.slice(6 * offset, 6 * (offset + 1))
      val rowAddress: Int = (address.head << 1) + address.last
      val colAddress: Int = address.slice(1, 5).foldLeft(0)(_ * 2 + _)
      val sTableRes: Byte = sTable(rowAddress * 16 + colAddress)
      @tailrec
      def converter(a: Byte, res: List[Byte] = List()): List[Byte] =
        if (a == 0 && res.length == 4) res // endless recursion in case of overflow
        else converter((a >> 1).toByte, (a & 1).toByte :: res)
      converter(sTableRes).toArray
    }

    @tailrec
    override def shiftedLeft(v: Array[Byte], i: Int): Array[Byte] =
      if (i <= 0) v else shiftedLeft(v.tail :+ v.head, i - 1)

    override def xored(a: Array[Byte], b: Array[Byte]): Array[Byte] =
      (a zip b).map { case (aa, bb) => ((aa + bb) % 2).toByte }

    override def toModel(data: Array[Byte]): Array[Byte] = {
      @tailrec
      def toBinary(a: Byte, res: List[Byte] = List()): List[Byte] =
        if ((a == 0 || a == -1) && res.length >= 8) res
        else toBinary((a >> 1).toByte, (a & 1).toByte :: res)
      data.toArray.flatMap(b => toBinary(b))
    }

    override def fromModel(v: Array[Byte]): Array[Byte] = {
      @tailrec
      def fromBinary(a: Array[Byte], res: Byte = 0): Byte =
        if (a.isEmpty) res
        else fromBinary(a.tail, (res * 2 + a.head).toByte)
      (for {
          i <- 0 until v.length / 8
          subArr = v.slice(i * 8, (i + 1) * 8)
          byte = fromBinary(subArr)
        } yield byte
      ).toArray
    }

    override def expanded(v: Array[Byte]): Array[Byte] =
      v.length match {
        case 64 => v
        case i => (v :+ (1: Byte)) ++ Array.ofDim[Byte](63 - i)
      }

    override def truncated(v: Array[Byte]): Array[Byte] = {
      @tailrec
      def truncFromReversed(v: Array[Byte]): Array[Byte] =
        v.head match {
          case 0 => truncFromReversed(v.tail)
          case 1 => v.tail
          case _ => v // this should not happen though
        }
      truncFromReversed(v.reverse).reverse
    }
  }
}

object DesObjectSyntax {
  implicit class DesObjectOps[A](value: A) {
    def halved(implicit desF: DesObject[A]): (A, A) = desF.halved(value)
    def combine(b: A)(implicit desF: DesObject[A]): A = desF.combined(value, b)
    def applyTable(table: Seq[Int])(implicit desF: DesObject[A]): A = desF.applyTable(value, table)
    def applySTable(sTable: Seq[Byte], offset: Int)(implicit desF: DesObject[A]): A = desF.applySTable(value, sTable, offset)
    def shiftLeft(i: Int)(implicit desF: DesObject[A]): A = desF.shiftedLeft(value, i)
    def xor(b: A)(implicit desF: DesObject[A]): A = desF.xored(value, b)

    def expand(implicit desF: DesObject[A]): A = desF.expanded(value)
    def trunc(implicit desF: DesObject[A]): A = desF.truncated(value)

    def fromModel(implicit desF: DesObject[A]): Array[Byte] = desF.fromModel(value)
  }
  implicit def toModel[A](data: Array[Byte])(implicit desF: DesObject[A]): A =
    desF.toModel(data)
}
