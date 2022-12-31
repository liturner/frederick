:: Frederick aims to make the use of Java as transparent
:: as possible, without resorting to platform dependent
:: compilations.
::
:: We will always use shortcuts installed in the start menu
:: and desktop with icons to this cmd.
::
:: START    - Runs without keeping CMD window open
:: %~dp0    - Is the directory holding this file
:: /B       - Should skip making any cmd windows
::
:: Set VM Options in the maven config for jlink!
START "Frederick" /B "%~dp0jre\bin\java" -Xmx1G -jar "%~dp0lib\fred-main-1.0.0-SNAPSHOT.jar" %*
