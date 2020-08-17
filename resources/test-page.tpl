<html>
  <head>
  </head>
  <body onload="__RUNNER__">
    <script type="text/javascript">
      window.addEventListener('error', function(event) {
        var started = window["CLJ_TESTS_STARTED"] || false;
        if(!started) {
          window["CLJ_FATAL_ERROR"] = event.message;
        }
      });
    </script>
    <script type="text/javascript">
      __JS__
    </script>
    </script>
  </body>
</html>
