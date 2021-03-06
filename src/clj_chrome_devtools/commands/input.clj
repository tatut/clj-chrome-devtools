(ns clj-chrome-devtools.commands.input
  (:require [clojure.spec.alpha :as s]
            [clj-chrome-devtools.impl.command :as cmd]
            [clj-chrome-devtools.impl.connection :as c]))

(s/def
 ::touch-point
 (s/keys
  :req-un
  [::x
   ::y]
  :opt-un
  [::radius-x
   ::radius-y
   ::rotation-angle
   ::force
   ::id]))

(s/def
 ::gesture-source-type
 #{"mouse" "touch" "default"})

(s/def
 ::time-since-epoch
 number?)
(defn
 dispatch-key-event
 "Dispatches a key event to the page.\n\nParameters map keys:\n\n\n  Key                       | Description \n  --------------------------|------------ \n  :type                     | Type of the key event.\n  :modifiers                | Bit field representing pressed modifier keys. Alt=1, Ctrl=2, Meta/Command=4, Shift=8\n(default: 0). (optional)\n  :timestamp                | Time at which the event occurred. (optional)\n  :text                     | Text as generated by processing a virtual key code with a keyboard layout. Not needed for\nfor `keyUp` and `rawKeyDown` events (default: \"\") (optional)\n  :unmodified-text          | Text that would have been generated by the keyboard if no modifiers were pressed (except for\nshift). Useful for shortcut (accelerator) key handling (default: \"\"). (optional)\n  :key-identifier           | Unique key identifier (e.g., 'U+0041') (default: \"\"). (optional)\n  :code                     | Unique DOM defined string value for each physical key (e.g., 'KeyA') (default: \"\"). (optional)\n  :key                      | Unique DOM defined string value describing the meaning of the key in the context of active\nmodifiers, keyboard layout, etc (e.g., 'AltGr') (default: \"\"). (optional)\n  :windows-virtual-key-code | Windows virtual key code (default: 0). (optional)\n  :native-virtual-key-code  | Native virtual key code (default: 0). (optional)\n  :auto-repeat              | Whether the event was generated from auto repeat (default: false). (optional)\n  :is-keypad                | Whether the event was generated from the keypad (default: false). (optional)\n  :is-system-key            | Whether the event was a system key event (default: false). (optional)\n  :location                 | Whether the event was from the left or right side of the keyboard. 1=Left, 2=Right (default:\n0). (optional)"
 ([]
  (dispatch-key-event
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [type
     modifiers
     timestamp
     text
     unmodified-text
     key-identifier
     code
     key
     windows-virtual-key-code
     native-virtual-key-code
     auto-repeat
     is-keypad
     is-system-key
     location]}]
  (dispatch-key-event
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [type
     modifiers
     timestamp
     text
     unmodified-text
     key-identifier
     code
     key
     windows-virtual-key-code
     native-virtual-key-code
     auto-repeat
     is-keypad
     is-system-key
     location]}]
  (cmd/command
   connection
   "Input"
   "dispatchKeyEvent"
   params
   {:key "key",
    :windows-virtual-key-code "windowsVirtualKeyCode",
    :unmodified-text "unmodifiedText",
    :type "type",
    :auto-repeat "autoRepeat",
    :modifiers "modifiers",
    :is-keypad "isKeypad",
    :code "code",
    :key-identifier "keyIdentifier",
    :native-virtual-key-code "nativeVirtualKeyCode",
    :timestamp "timestamp",
    :location "location",
    :is-system-key "isSystemKey",
    :text "text"})))

(s/fdef
 dispatch-key-event
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::type]
    :opt-un
    [::modifiers
     ::timestamp
     ::text
     ::unmodified-text
     ::key-identifier
     ::code
     ::key
     ::windows-virtual-key-code
     ::native-virtual-key-code
     ::auto-repeat
     ::is-keypad
     ::is-system-key
     ::location]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::type]
    :opt-un
    [::modifiers
     ::timestamp
     ::text
     ::unmodified-text
     ::key-identifier
     ::code
     ::key
     ::windows-virtual-key-code
     ::native-virtual-key-code
     ::auto-repeat
     ::is-keypad
     ::is-system-key
     ::location])))
 :ret
 (s/keys))

(defn
 insert-text
 "This method emulates inserting text that doesn't come from a key press,\nfor example an emoji keyboard or an IME.\n\nParameters map keys:\n\n\n  Key   | Description \n  ------|------------ \n  :text | The text to insert."
 ([]
  (insert-text
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [text]}]
  (insert-text
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [text]}]
  (cmd/command
   connection
   "Input"
   "insertText"
   params
   {:text "text"})))

(s/fdef
 insert-text
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::text]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::text])))
 :ret
 (s/keys))

(defn
 dispatch-mouse-event
 "Dispatches a mouse event to the page.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :type         | Type of the mouse event.\n  :x            | X coordinate of the event relative to the main frame's viewport in CSS pixels.\n  :y            | Y coordinate of the event relative to the main frame's viewport in CSS pixels. 0 refers to\nthe top of the viewport and Y increases as it proceeds towards the bottom of the viewport.\n  :modifiers    | Bit field representing pressed modifier keys. Alt=1, Ctrl=2, Meta/Command=4, Shift=8\n(default: 0). (optional)\n  :timestamp    | Time at which the event occurred. (optional)\n  :button       | Mouse button (default: \"none\"). (optional)\n  :buttons      | A number indicating which buttons are pressed on the mouse when a mouse event is triggered.\nLeft=1, Right=2, Middle=4, Back=8, Forward=16, None=0. (optional)\n  :click-count  | Number of times the mouse button was clicked (default: 0). (optional)\n  :delta-x      | X delta in CSS pixels for mouse wheel event (default: 0). (optional)\n  :delta-y      | Y delta in CSS pixels for mouse wheel event (default: 0). (optional)\n  :pointer-type | Pointer type (default: \"mouse\"). (optional)"
 ([]
  (dispatch-mouse-event
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [type
     x
     y
     modifiers
     timestamp
     button
     buttons
     click-count
     delta-x
     delta-y
     pointer-type]}]
  (dispatch-mouse-event
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [type
     x
     y
     modifiers
     timestamp
     button
     buttons
     click-count
     delta-x
     delta-y
     pointer-type]}]
  (cmd/command
   connection
   "Input"
   "dispatchMouseEvent"
   params
   {:y "y",
    :click-count "clickCount",
    :buttons "buttons",
    :delta-y "deltaY",
    :button "button",
    :type "type",
    :delta-x "deltaX",
    :modifiers "modifiers",
    :pointer-type "pointerType",
    :x "x",
    :timestamp "timestamp"})))

(s/fdef
 dispatch-mouse-event
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::type
     ::x
     ::y]
    :opt-un
    [::modifiers
     ::timestamp
     ::button
     ::buttons
     ::click-count
     ::delta-x
     ::delta-y
     ::pointer-type]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::type
     ::x
     ::y]
    :opt-un
    [::modifiers
     ::timestamp
     ::button
     ::buttons
     ::click-count
     ::delta-x
     ::delta-y
     ::pointer-type])))
 :ret
 (s/keys))

(defn
 dispatch-touch-event
 "Dispatches a touch event to the page.\n\nParameters map keys:\n\n\n  Key           | Description \n  --------------|------------ \n  :type         | Type of the touch event. TouchEnd and TouchCancel must not contain any touch points, while\nTouchStart and TouchMove must contains at least one.\n  :touch-points | Active touch points on the touch device. One event per any changed point (compared to\nprevious touch event in a sequence) is generated, emulating pressing/moving/releasing points\none by one.\n  :modifiers    | Bit field representing pressed modifier keys. Alt=1, Ctrl=2, Meta/Command=4, Shift=8\n(default: 0). (optional)\n  :timestamp    | Time at which the event occurred. (optional)"
 ([]
  (dispatch-touch-event
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [type touch-points modifiers timestamp]}]
  (dispatch-touch-event
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [type touch-points modifiers timestamp]}]
  (cmd/command
   connection
   "Input"
   "dispatchTouchEvent"
   params
   {:type "type",
    :touch-points "touchPoints",
    :modifiers "modifiers",
    :timestamp "timestamp"})))

(s/fdef
 dispatch-touch-event
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::type
     ::touch-points]
    :opt-un
    [::modifiers
     ::timestamp]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::type
     ::touch-points]
    :opt-un
    [::modifiers
     ::timestamp])))
 :ret
 (s/keys))

(defn
 emulate-touch-from-mouse-event
 "Emulates touch event from the mouse event parameters.\n\nParameters map keys:\n\n\n  Key          | Description \n  -------------|------------ \n  :type        | Type of the mouse event.\n  :x           | X coordinate of the mouse pointer in DIP.\n  :y           | Y coordinate of the mouse pointer in DIP.\n  :button      | Mouse button.\n  :timestamp   | Time at which the event occurred (default: current time). (optional)\n  :delta-x     | X delta in DIP for mouse wheel event (default: 0). (optional)\n  :delta-y     | Y delta in DIP for mouse wheel event (default: 0). (optional)\n  :modifiers   | Bit field representing pressed modifier keys. Alt=1, Ctrl=2, Meta/Command=4, Shift=8\n(default: 0). (optional)\n  :click-count | Number of times the mouse button was clicked (default: 0). (optional)"
 ([]
  (emulate-touch-from-mouse-event
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [type x y button timestamp delta-x delta-y modifiers click-count]}]
  (emulate-touch-from-mouse-event
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [type x y button timestamp delta-x delta-y modifiers click-count]}]
  (cmd/command
   connection
   "Input"
   "emulateTouchFromMouseEvent"
   params
   {:y "y",
    :click-count "clickCount",
    :delta-y "deltaY",
    :button "button",
    :type "type",
    :delta-x "deltaX",
    :modifiers "modifiers",
    :x "x",
    :timestamp "timestamp"})))

(s/fdef
 emulate-touch-from-mouse-event
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::type
     ::x
     ::y
     ::button]
    :opt-un
    [::timestamp
     ::delta-x
     ::delta-y
     ::modifiers
     ::click-count]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::type
     ::x
     ::y
     ::button]
    :opt-un
    [::timestamp
     ::delta-x
     ::delta-y
     ::modifiers
     ::click-count])))
 :ret
 (s/keys))

(defn
 set-ignore-input-events
 "Ignores input events (useful while auditing page).\n\nParameters map keys:\n\n\n  Key     | Description \n  --------|------------ \n  :ignore | Ignores input events processing when set to true."
 ([]
  (set-ignore-input-events
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [ignore]}]
  (set-ignore-input-events
   (c/get-current-connection)
   params))
 ([connection {:as params, :keys [ignore]}]
  (cmd/command
   connection
   "Input"
   "setIgnoreInputEvents"
   params
   {:ignore "ignore"})))

(s/fdef
 set-ignore-input-events
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::ignore]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::ignore])))
 :ret
 (s/keys))

(defn
 synthesize-pinch-gesture
 "Synthesizes a pinch gesture over a time period by issuing appropriate touch events.\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :x                   | X coordinate of the start of the gesture in CSS pixels.\n  :y                   | Y coordinate of the start of the gesture in CSS pixels.\n  :scale-factor        | Relative scale factor after zooming (>1.0 zooms in, <1.0 zooms out).\n  :relative-speed      | Relative pointer speed in pixels per second (default: 800). (optional)\n  :gesture-source-type | Which type of input events to be generated (default: 'default', which queries the platform\nfor the preferred input type). (optional)"
 ([]
  (synthesize-pinch-gesture
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys [x y scale-factor relative-speed gesture-source-type]}]
  (synthesize-pinch-gesture
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys [x y scale-factor relative-speed gesture-source-type]}]
  (cmd/command
   connection
   "Input"
   "synthesizePinchGesture"
   params
   {:x "x",
    :y "y",
    :scale-factor "scaleFactor",
    :relative-speed "relativeSpeed",
    :gesture-source-type "gestureSourceType"})))

(s/fdef
 synthesize-pinch-gesture
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::x
     ::y
     ::scale-factor]
    :opt-un
    [::relative-speed
     ::gesture-source-type]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::x
     ::y
     ::scale-factor]
    :opt-un
    [::relative-speed
     ::gesture-source-type])))
 :ret
 (s/keys))

(defn
 synthesize-scroll-gesture
 "Synthesizes a scroll gesture over a time period by issuing appropriate touch events.\n\nParameters map keys:\n\n\n  Key                      | Description \n  -------------------------|------------ \n  :x                       | X coordinate of the start of the gesture in CSS pixels.\n  :y                       | Y coordinate of the start of the gesture in CSS pixels.\n  :x-distance              | The distance to scroll along the X axis (positive to scroll left). (optional)\n  :y-distance              | The distance to scroll along the Y axis (positive to scroll up). (optional)\n  :x-overscroll            | The number of additional pixels to scroll back along the X axis, in addition to the given\ndistance. (optional)\n  :y-overscroll            | The number of additional pixels to scroll back along the Y axis, in addition to the given\ndistance. (optional)\n  :prevent-fling           | Prevent fling (default: true). (optional)\n  :speed                   | Swipe speed in pixels per second (default: 800). (optional)\n  :gesture-source-type     | Which type of input events to be generated (default: 'default', which queries the platform\nfor the preferred input type). (optional)\n  :repeat-count            | The number of times to repeat the gesture (default: 0). (optional)\n  :repeat-delay-ms         | The number of milliseconds delay between each repeat. (default: 250). (optional)\n  :interaction-marker-name | The name of the interaction markers to generate, if not empty (default: \"\"). (optional)"
 ([]
  (synthesize-scroll-gesture
   (c/get-current-connection)
   {}))
 ([{:as params,
    :keys
    [x
     y
     x-distance
     y-distance
     x-overscroll
     y-overscroll
     prevent-fling
     speed
     gesture-source-type
     repeat-count
     repeat-delay-ms
     interaction-marker-name]}]
  (synthesize-scroll-gesture
   (c/get-current-connection)
   params))
 ([connection
   {:as params,
    :keys
    [x
     y
     x-distance
     y-distance
     x-overscroll
     y-overscroll
     prevent-fling
     speed
     gesture-source-type
     repeat-count
     repeat-delay-ms
     interaction-marker-name]}]
  (cmd/command
   connection
   "Input"
   "synthesizeScrollGesture"
   params
   {:y "y",
    :y-distance "yDistance",
    :x-distance "xDistance",
    :y-overscroll "yOverscroll",
    :speed "speed",
    :repeat-delay-ms "repeatDelayMs",
    :x-overscroll "xOverscroll",
    :gesture-source-type "gestureSourceType",
    :prevent-fling "preventFling",
    :x "x",
    :interaction-marker-name "interactionMarkerName",
    :repeat-count "repeatCount"})))

(s/fdef
 synthesize-scroll-gesture
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::x
     ::y]
    :opt-un
    [::x-distance
     ::y-distance
     ::x-overscroll
     ::y-overscroll
     ::prevent-fling
     ::speed
     ::gesture-source-type
     ::repeat-count
     ::repeat-delay-ms
     ::interaction-marker-name]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::x
     ::y]
    :opt-un
    [::x-distance
     ::y-distance
     ::x-overscroll
     ::y-overscroll
     ::prevent-fling
     ::speed
     ::gesture-source-type
     ::repeat-count
     ::repeat-delay-ms
     ::interaction-marker-name])))
 :ret
 (s/keys))

(defn
 synthesize-tap-gesture
 "Synthesizes a tap gesture over a time period by issuing appropriate touch events.\n\nParameters map keys:\n\n\n  Key                  | Description \n  ---------------------|------------ \n  :x                   | X coordinate of the start of the gesture in CSS pixels.\n  :y                   | Y coordinate of the start of the gesture in CSS pixels.\n  :duration            | Duration between touchdown and touchup events in ms (default: 50). (optional)\n  :tap-count           | Number of times to perform the tap (e.g. 2 for double tap, default: 1). (optional)\n  :gesture-source-type | Which type of input events to be generated (default: 'default', which queries the platform\nfor the preferred input type). (optional)"
 ([]
  (synthesize-tap-gesture
   (c/get-current-connection)
   {}))
 ([{:as params, :keys [x y duration tap-count gesture-source-type]}]
  (synthesize-tap-gesture
   (c/get-current-connection)
   params))
 ([connection
   {:as params, :keys [x y duration tap-count gesture-source-type]}]
  (cmd/command
   connection
   "Input"
   "synthesizeTapGesture"
   params
   {:x "x",
    :y "y",
    :duration "duration",
    :tap-count "tapCount",
    :gesture-source-type "gestureSourceType"})))

(s/fdef
 synthesize-tap-gesture
 :args
 (s/or
  :no-args
  (s/cat)
  :just-params
  (s/cat
   :params
   (s/keys
    :req-un
    [::x
     ::y]
    :opt-un
    [::duration
     ::tap-count
     ::gesture-source-type]))
  :connection-and-params
  (s/cat
   :connection
   (s/?
    c/connection?)
   :params
   (s/keys
    :req-un
    [::x
     ::y]
    :opt-un
    [::duration
     ::tap-count
     ::gesture-source-type])))
 :ret
 (s/keys))
