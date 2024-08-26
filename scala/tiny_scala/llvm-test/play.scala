case class B(a: A)

case class A(v1: Int, v2: Double)

object MyFunctions {
  def asd: Int = 3
}

object Main extends App {
  val b: B = B(A(1, 3.0))
  MyFunctions.asd()
}
