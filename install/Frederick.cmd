:: Frederick aims to make the use of Java as transparent
:: as possible, without resorting to platform dependent
:: compilations.
::
:: We will always use shortcuts installed in the start menu
:: and desktop with icons to this cmd.
::
:: %~dp0    - Is the directory holding this file
:: -d64     - 64 bit required. We anyway ship 64bit javaw
:: -Xmx128m - Max Heap Size. Its a lightweight app...
%~dp0jre\bin\javaw -Xmx128m -d64 -jar %~dp0lib\Frederick.jar