# clj-chrome-devtools

[![Clojars Project](https://img.shields.io/clojars/v/clj-chrome-devtools.svg)](https://clojars.org/clj-chrome-devtools)

clj-chrome-devtools is a simple library for controlling a headless Chrome with the
Chrome DevTools Protocol. The protocol is based on a websocket connection between
clojure and chrome. All the functions are automatically generated from the protocol
specification JSON files.

## API Docs

See codox generated [API docs](https://tatut.github.io/clj-chrome-devtools/api/index.html).

## Example usage

The following shows a simple REPL usage, navigating to a page and inspecting content.
The connection is the first parameter of all calls. You can also set the connection to use with
`set-current-connection!` and omit the connection parameter for convenience.


```clojure
clj-chrome-devtools.core> (def c (connect "localhost" 9222))
#'clj-chrome-devtools.core/c
clj-chrome-devtools.core> (require '[clj-chrome-devtools.commands.page :as page]
                                   '[clj-chrome-devtools.commands.dom :as dom])
nil
clj-chrome-devtools.core> (page/navigate c {:url "http://webjure.org/"})
{:frame-id "68439.1"}
clj-chrome-devtools.core> (dom/get-document c {:depth 1})
{:root
 {:children
  [{:node-type 1,
    :node-id 2,
    :backend-node-id 4,
    :parent-id 1,
    :node-name "HTML",
    :node-value "",
    :frame-id "68439.1",
    :local-name "html",
    :child-node-count 2,
    :attributes []}],
  :document-url "http://webjure.org/",
  :node-type 9,
  :base-url "http://webjure.org/",
  :node-id 1,
  :backend-node-id 3,
  :node-name "#document",
  :node-value "",
  :xml-version "",
  :local-name "",
  :child-node-count 1}}
clj-chrome-devtools.core> (use 'clojure.repl)
nil
clj-chrome-devtools.core> (doc dom/get-outer-html)
-------------------------
clj-chrome-devtools.commands.dom/get-outer-html
([] [{:as params, :keys [node-id]}] [connection {:as params, :keys [node-id]}])
  Returns node's HTML markup.

Parameters map keys:
  :node-id              Id of the node to get markup for.

Return map keys:
  :outer-html           Outer HTML markup.
nil
clj-chrome-devtools.core> (dom/get-outer-html c {:node-id 1})
{:outer-html
 "<html><head>\n    <title>Webjure</title>\n  </head>\n  <body>\n    Coming soon-ish!\n  \n\n</body></html>"}
```
