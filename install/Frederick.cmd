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
START "Frederick" /B "%~dp0jre\bin\javaw" -Xmx128m -jar "%~dp0lib\frederick.jar" %*
