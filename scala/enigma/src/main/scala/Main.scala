import Enigma.ByteArrayReflectorExt.{byteArrayReflectorFileableInstance, byteArrayReflectorRandomGenerator}
import Enigma.{ByteArrayReflector, ByteArrayRotor, ByteEnigma}
import Enigma.ByteArrayRotorExt.{rotorFileableInstance, rotorRandomGenerator}
import Ext.FileableSyntax.{ToFileOps, fromFile}
import Ext.Files.using
import scopt.OParser

import java.io.{BufferedReader, BufferedWriter, FileInputStream, FileOutputStream, FileReader, FileWriter}
import java.nio.charset.Charset
import scala.sys.exit

case class Config(
  mode: Option[ConfigModes] = None,
  rotorPrefix: String = "",
  reflectorPrefix: String = "",
  rotorCount: Int = 3,
  inputFile: String = "",
  outputFile: String = "",
)

sealed trait ConfigModes
case object ConfigModeGenerate extends ConfigModes
case object ConfigModeStart extends ConfigModes

object Main {
  private val builder = OParser.builder[Config]
  private val argParser = {
    import builder._
    OParser.sequence(
      programName("Enigma"),
      cmd("generate")
        .action((_, c) => c.copy(mode = Some(ConfigModeGenerate)))
        .text(""),
      cmd("start")
        .action((_, c) => c.copy(mode = Some(ConfigModeStart)))
        .text("Use machine to cypher messages")
        .children(
          opt[String]('i', "input")
            .required()
            .action((a, c) => c.copy(inputFile = a))
            .text("File to cypher"),
          opt[String]('o', "output")
            .required()
            .action((a, c) => c.copy(outputFile = a))
            .text("Cyphered information"),
        ),
      opt[String]("rotor-prefix")
        .required()
        .action((a, c) => c.copy(rotorPrefix = a))
        .text("Prefix for rotor-file"),
      opt[String]("refl-prefix")
        .required()
        .action((a, c) => c.copy(reflectorPrefix = a))
        .text("Prefix for reflector-file"),
      opt[Int]('n', "r-count")
        .required()
        .action((a, c) => c.copy(rotorCount = a))
        .text("Count of rotor files"),
    )
  }

  private def startEnigma(config: Config): Unit = {
    val rotorFilenames = for (i <- 1 to config.rotorCount) yield s"${config.rotorPrefix}$i"
    val rotors: List[ByteArrayRotor] = (rotorFilenames map { filename =>
      fromFile[ByteArrayRotor](filename) match {
        case Some(v) => v
        case _ => println(s"Error while loading rotor from $filename"); return
      }
    }).toList
    val reflector: ByteArrayReflector = fromFile[ByteArrayReflector](config.reflectorPrefix) match {
      case Some(v) => v
      case _ => println(s"Error while loading rotor from ${config.reflectorPrefix}"); return
    }
    val enigma: ByteEnigma = ByteEnigma(rotors, reflector)

    using(new FileInputStream(config.inputFile)) {inputFile =>
      using(new FileOutputStream(config.outputFile)) { outputFile => {
        var c = inputFile.read
        while (c != -1) {
          outputFile.write(enigma.cypherAndGo(c.toByte).toInt)
          c = inputFile.read
        }
      }
      }
    }
  }

  private def startGenerator(config: Config): Unit = {
    for (i <- 1 to config.rotorCount)
      ByteArrayRotor.gen.toFile(s"${config.rotorPrefix}$i")
    ByteArrayReflector.gen.toFile(s"${config.reflectorPrefix}")
  }

  def main(args: Array[String]): Unit = {
    OParser.parse(argParser, args, Config()) match {
      case Some(config) =>
        config.mode match {
          case Some(mode) =>
            mode match {
              case ConfigModeGenerate => startGenerator(config)
              case ConfigModeStart => startEnigma(config)
            }
          case None =>
            println("Application mode is not set")
            exit(-1)
        }
      case None =>
        exit(-1)
    }
  }
}