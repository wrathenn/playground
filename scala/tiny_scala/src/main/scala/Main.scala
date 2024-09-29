package com.wrathenn.compilers

import context.TranslationContext

import cats.effect.{ExitCode, IO, IOApp}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import scopt.OParser

import java.nio.file.{Files, Paths}
import scala.io.Source
import scala.sys.exit

object Main extends IOApp {
  case class Config(
    file: String = "program.tinyScala",
    output: String = "program.ll",
  )

  private val builder = OParser.builder[Config]
  private val argParser = {
    import builder._
    OParser.sequence(
      programName("MiniScala compiler"),
      cmd("compile")
        .children(
          opt[String]('f', "file")
            .action((f, c) => c.copy(file = f) )
            .text("File to compile"),
          opt[String]('o', "output")
            .action((o, c) => c.copy(output = o))
            .text("Output llvm ir file")
        )
    )
  }

  private def readFile(path: String): IO[String] =
    IO blocking {
      val source = scala.io.Source.fromFile(path)
      val res = source.getLines().mkString("\n")
      source.close()
      res
    }

  private def compile(sourceFilename: String, outputFilename: String): IO[Unit] = for {
    code <- readFile(sourceFilename)

    charStream <- IO delay CharStreams.fromString(code)
    lexer <- IO delay new TinyScalaLexer(charStream)
    tokenStream <- IO delay new CommonTokenStream(lexer)
    parser: TinyScalaParser <- IO delay new TinyScalaParser(tokenStream)

    translationContext = TranslationContext.create
    llvmCode <- IO delay ProgramBuilder.buildProgram(context = translationContext, parser = parser)
    _ <- IO blocking {
      Files.writeString(
        Paths.get(outputFilename),
        llvmCode
      )
    }
  } yield ()

  override def run(args: List[String]): IO[ExitCode] = {
    OParser.parse(argParser, args, Config()) match {
      case Some(config) => for {
        _ <- compile(config.file, config.output)
      } yield ExitCode.Success
      case None => exit(-1)
    }
  }
}
