import javafx.event.{ActionEvent, EventHandler}
import org.sameersingh.scalaplot.Implicits._
import scalafx.application.JFXApp3
import scalafx.beans.property.StringProperty
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.stage.Stage

object Main extends JFXApp3 {
  val bgColor = Color.rgb(38, 38, 38)

  def normalDistributionApp(): Stage = {
    val aEntry = new TextField {
      margin = Insets(3)
    }
    val bEntry = new TextField {
      margin = Insets(3)
    }
    val stepEntry = new TextField {
      margin = Insets(3)
    }
    new Stage {
      title = "Равномерное распределение"
      scene = new Scene {
        fill = bgColor
        content = new VBox {
          padding = Insets(10, 10, 10, 10)
          children = Seq(
            new Label {
              text = "a"
              textFill = Color.White
            },
            aEntry,
            new Label {
              text = "b"
              textFill = Color.White
            },
            bEntry,
            new Label {
              text = "Шаг"
              textFill = Color.White
            },
            stepEntry,
            new Button {
              text = "Построить"
              margin = Insets(3)
              maxWidth = Double.MaxValue
              fill = bgColor
              onAction = new EventHandler[ActionEvent] {
                override def handle(event: ActionEvent): Unit = {
                  try {
                    val distribution = new NormalDistribution(
                      aEntry.getText.toDouble,
                      bEntry.getText.toDouble
                    )
                    output(GUI, distribution.distributionChart(stepEntry.getText.toDouble, "Функция распределения"))
                    output(GUI, distribution.densityChart(stepEntry.getText.toDouble, "Функция плотности"))
                  }
                  catch {
                    case e: NumberFormatException => new Alert(AlertType.Error) {
                      title = "Ошибка!"
                      headerText = s"Некорректный ввод: $e"
                      contentText = "Проверьте введенные значения"
                    }.showAndWait()
                  }
                }
              }
            }
          )
        }
      }
    }
  }

  def exponentialDistributionApp(): Stage = {
    val lambdaEntry = new TextField {
      margin = Insets(3)
    }
    val leftBorderEntry = new TextField {
      margin = Insets(3)
    }
    val rightBorderEntry = new TextField {
      margin = Insets(3)
    }
    val stepEntry = new TextField {
      margin = Insets(3)
    }
    new Stage {
      title = "Экспоненциальное распределение"
      scene = new Scene {
        fill = bgColor
        content = new VBox {
          padding = Insets(10, 10, 10, 10)
          children = Seq(
            new Label {
              text = "Лямбда"
              textFill = Color.White
            },
            lambdaEntry,
            new Label {
              text = "Левая граница"
              textFill = Color.White
            },
            leftBorderEntry,
            new Label {
              text = "Правая граница"
              textFill = Color.White
            },
            rightBorderEntry,
            new Label {
              text = "Шаг"
              textFill = Color.White
            },
            stepEntry,
            new Button {
              text = "Построить"
              fill = bgColor
              maxWidth = Double.MaxValue
              margin = Insets(3)
              onAction = new EventHandler[ActionEvent] {
                override def handle(event: ActionEvent): Unit = {
                  try {
                    val distribution = new ExponentialDistribution(
                      lambdaEntry.getText.toDouble,
                      leftBorderEntry.getText.toDouble,
                      rightBorderEntry.getText.toDouble
                    )
                    output(GUI, distribution.distributionChart(stepEntry.getText.toDouble, "Функция распределения"))
                    output(GUI, distribution.densityChart(stepEntry.getText.toDouble, "Функция плотности"))
                  }
                  catch {
                    case e: NumberFormatException => new Alert(AlertType.Error) {
                      title = "Ошибка!"
                      headerText = s"Некорректный ввод: $e"
                      contentText = "Проверьте введенные значения"
                    }.showAndWait()
                  }
                }
              }
            }
          )
        }
      }
    }
  }

  override def start(): Unit = {
    val currentMode = StringProperty("")
    val functions = Map[String, () => Unit](
      "Равномерное" -> (() => normalDistributionApp().show()),
      "Экспоненциальное" -> (() => exponentialDistributionApp().show())
    )

    val functionChooser = new ComboBox[String](functions.keys.toSeq)
    functionChooser.onAction = new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = {
        currentMode.set(functionChooser.selectionModel().getSelectedItem)
      }
    }

    new JFXApp3.PrimaryStage {
      title.value = "Графики функций распределения"
      width = 400
      scene = new Scene {
        fill = bgColor
        content = new VBox {
          padding = Insets(10, 10, 10, 10)
          alignment = Pos.Center
          children = Seq(
            new Label {
              text = "Тип распределения"
              textFill = Color.White
            },
            functionChooser,
            new Button {
              text = "Начать построение"
              maxWidth = Double.MaxValue
              margin = Insets(3)
              onAction = new EventHandler[ActionEvent] {
                override def handle(event: ActionEvent): Unit = {
                  functions.get(currentMode.get()) match {
                    case Some(x) => x()
                    case None => new Alert(AlertType.Error) {
                      title = "Ошибка!"
                      headerText = "Не был выбран тип распределения"
                      contentText = "Выберите тип распределения в выпадающем меню"
                    }.showAndWait()
                  }
                }
              }
            }
          )
        }
      }
    }
  }
}
