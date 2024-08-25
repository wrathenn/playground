#!/bin/bash

this_path=$(pwd)

java -jar antlr-4.13.2-complete.jar -o \
  $this_path/src/main/java/com/wrathenn/compilers \
  -package com.wrathenn.compilers \
  -listener -visitor \
  -lib $this_path/grammar \
  $this_path/grammar/TinyScala.g4 \

mv $this_path/src/main/java/com/wrathenn/compilers/* $this_path/src/main/java/ \

rm -r $this_path/src/main/java/com/
