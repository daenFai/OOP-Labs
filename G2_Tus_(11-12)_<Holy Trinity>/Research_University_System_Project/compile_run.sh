#!/usr/bin/env bash
set -e
rm -rf out
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out Main
