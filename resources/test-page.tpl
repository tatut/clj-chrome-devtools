<html>
  <head>
  </head>
  <body onload="clj_chrome_devtools_runner.run_chrome_tests();">
    <script type="text/javascript">
      window.addEventListener('error', function(event) {
        var started = window["CLJ_TESTS_STARTED"] || false;
        if(!started) {
          window["CLJ_FATAL_ERROR"] = event.message;
        }
      });
    </script>
    <script type="text/javascript" src="__JS__">
    </script>
  </body>
</html>
