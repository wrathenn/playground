import org.sameersingh.scalaplot.{MemXYSeries, XYChart, XYData}
import org.sameersingh.scalaplot.Implicits._

trait Distribution {
  def densityChart(step: Double, title: String = ""): XYChart

  def distributionChart(step: Double, title: String = ""): XYChart
}

class NormalDistribution(
  a: Double,
  b: Double,
  offset: Double = 1
) extends Distribution {
  private def distributionFunction(x: Double): Double =
    if (x < a) 0
    else if (x < b) (x - a) / (b - a)
    else 1

  private def densityFunction(x: Double): Double =
    if (x < a || x > b) 0
    else 1 / (b - a)

  override def distributionChart(step: Double, title: String = ""): XYChart = {
    val x: Seq[Double] = a - offset until b + offset by step
    val y = x map distributionFunction
    xyChart(x -> y, title = title)
  }

  override def densityChart(step: Double, title: String = ""): XYChart = {
    val x: Seq[Double] = a - offset until b + offset by step
    xyChart(x -> (densityFunction(_)), title = title)
  }
}

class ExponentialDistribution(
  lambda: Double,
  leftBorder: Double,
  rightBorder: Double
) extends Distribution {
  private def densityFunction(x: Double): Double =
    if (x < 0) 0
    else lambda * math.exp(-lambda * x)

  private def distributionFunction(x: Double): Double =
    if (x < 0) 0
    else 1 - math.exp(-lambda * x)

  override def densityChart(step: Double, title: String = ""): XYChart = {
    val x: Seq[Double] = leftBorder until rightBorder by step
    xyChart(x -> (densityFunction(_)), title = title)
  }

  override def distributionChart(step: Double, title: String = ""): XYChart = {
    val x: Seq[Double] = leftBorder until rightBorder by step
    xyChart(x -> (distributionFunction(_)), title = title)
  }
}

object GraphTest extends App {
  val normalDistribution = new NormalDistribution(2, 10)
  output(GUI, normalDistribution.distributionChart(0.1))
  val exponentialDistribution = new ExponentialDistribution(3, 0, 20)
  output(GUI, exponentialDistribution.distributionChart(0.1))
}
