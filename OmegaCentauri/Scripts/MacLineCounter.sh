#!/bin/sh
# script for counting words on my Mac.

cd /Users/Kieburtz/Omega-Centauri/OmegaCentauri/src/MainPackage

find . -name "*.java" -exec cat {} \; | wc -l 