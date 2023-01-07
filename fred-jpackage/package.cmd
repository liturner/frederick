REM https://docs.oracle.com/en/java/javase/14/docs/specs/man/jpackage.html
jpackage -n Frederick -i fred-jpackage/target/dependency --main-class de.turnertech.frederick.main.Application --main-jar fred-main-1.0.1-SNAPSHOT.jar -d fred-jpackage/target --vendor TurnerTech --icon fred-jpackage/frederick-logo.ico --license-file EULA.txt --win-shortcut --win-menu
