package com.wrathenn.compilers
package regex

import cats.effect.IO
import com.wrathenn.compilers.regex.automata.Automata
import com.wrathenn.compilers.regex.automata.Ext.AutomataExt
import edu.uci.ics.jung
import edu.uci.ics.jung.algorithms.layout.{CircleLayout, DAGLayout, FRLayout, FRLayout2, ISOMLayout, KKLayout, SpringLayout, SpringLayout2, StaticLayout}
import edu.uci.ics.jung.graph.util.Graphs
import edu.uci.ics.jung.graph.{DirectedSparseGraph, DirectedSparseMultigraph, Graph, SparseMultigraph}
import edu.uci.ics.jung.visualization.VisualizationViewer
import edu.uci.ics.jung.visualization.control.{DefaultModalGraphMouse, ModalGraphMouse}
import edu.uci.ics.jung.visualization.renderers.{DefaultEdgeLabelRenderer, DefaultVertexLabelRenderer}

import java.awt.{Color, Dimension, Font, Paint}
import javax.swing.{JFrame, WindowConstants}
import javax.xml.transform.Transformer

object AutomataDrawer {
  case class Edge(
    from: Automata.StateId,
    to: Automata.StateId,
    symbol: Char,
  )

  def drawAutomata(automata: Automata): IO[JFrame] = for {
    graph <- IO delay new DirectedSparseMultigraph[Automata.State, Edge]
    _ <- IO delay {
      automata.states.foreach { case (id, s) => graph.addVertex(s) }
      automata.impureMap { state => graph.addVertex(state) }
      automata.impureMap { state => state.transitions.foreach { case (c, id) =>
        val state2 = automata.states(id)
        graph.addEdge(Edge(state.id, id, c), state, state2)
      } }
    }

    frame <- IO delay new JFrame()
    _ <- IO delay {
      frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
      val size = new Dimension(900, 900)
      val vv: VisualizationViewer[Automata.State, Edge] = new VisualizationViewer(new KKLayout(graph), size)
      val vertexRenderer = new DefaultVertexLabelRenderer(Color.green)
      vertexRenderer.setText("a")
      vv.getRenderContext.setVertexFontTransformer((i: Automata.State) => new Font("arial", Font.BOLD, 24))
      vv.getRenderContext.setEdgeLabelTransformer((i: Edge) => i.symbol.toString)
      vv.getRenderContext.setEdgeFontTransformer((i: Edge) => new Font("arial", Font.ITALIC, 20))
      vv.getRenderContext.setVertexLabelTransformer { (i: Automata.State) =>
        val terminal = if (i.isTerminal) "[END]" else ""
        val beginning = if (automata.beginningId == i.id) "[START]" else ""
        s"${i.id} ${ List(beginning, terminal).filter(_.nonEmpty).mkString(" ") }"
      }
      vv.getRenderContext.setVertexFillPaintTransformer { i: Automata.State =>
        if (i.isTerminal) {
          Color.BLACK
        } else if (automata.beginningId == i.id) {
          Color.WHITE
        } else Color.GREEN
      }

      val graphMouse = new DefaultModalGraphMouse();
      graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING)
      vv.setGraphMouse(graphMouse)
      frame.getContentPane.add(vv)
      frame.setSize(size)
      frame.setLocationRelativeTo(null)
    }

  } yield frame
}

object Visualization extends App {
  val graph = new DirectedSparseGraph[String, String]
  graph.addVertex("test1")
  graph.addVertex("test2")
  graph.addEdge("tt", "test1", "test2")
  graph.addEdge("ttt", "test1", "test1")

  val frame: JFrame = new JFrame()
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  val size = new Dimension(1640, 720)
  val vv: VisualizationViewer[String, String] = new VisualizationViewer(new CircleLayout(graph), size)
  val vertexRenderer = new DefaultVertexLabelRenderer(Color.green)
  vertexRenderer.setText("a")
  vv.getRenderContext.setVertexFontTransformer((i: String) => new Font("arial", Font.BOLD, 24))
  vv.getRenderContext.setEdgeLabelTransformer((i: String) => s"E[$i]")
  vv.getRenderContext.getEdgeLabelClosenessTransformer
  vv.getRenderContext.setVertexLabelTransformer((i: String) => i)

  val graphMouse = new DefaultModalGraphMouse();
  graphMouse.setMode(ModalGraphMouse.Mode.PICKING)
  vv.setGraphMouse(graphMouse)

  frame.getContentPane.add(vv)
  frame.setSize(size)
  frame.setLocationRelativeTo(null)
  frame.setVisible(true)
}
