#!/bin/zsh
java -jar ./target/scala-2.13/miniScala.jar compile -f program.tinyScala -o program.ll
clang program.ll -o program.out
