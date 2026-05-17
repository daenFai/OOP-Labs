#!/usr/bin/env bash
set -e
rm -rf docs
mkdir -p docs
javadoc -d docs $(find src -name "*.java")
echo "JavaDoc generated in docs/index.html"
