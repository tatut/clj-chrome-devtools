#!/bin/sh

git clone https://github.com/ChromeDevTools/devtools-protocol

clojure -m clj-chrome-devtools.impl.define

rm -rf devtools-protocol
