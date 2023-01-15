[Download Frederick](https://github.com/liturner/frederick/releases)

[![Github All Releases](https://img.shields.io/github/downloads/liturner/frederick/total.svg)]()


# Links

- [Product Site](https://liturner.github.io/frederick/)
- [GitHub Project](https://github.com/liturner/frederick)

# Project Type

- Maven
- Java 17
- VS Code (optionally)

# Project Aims

We aim to provide a set of tools to help with the missions and deployments of the German Federal Agency for Technical Relief. The project aims to produce software which fulfills stringent security and data protection checks.

- Stability of the software is preferred over quantity of features.
- The software should only be configurable where necessary.
- The software is to be used in emergency situations. Pretty comes second to fast.

# Architecture

The application aims to have a fairly minimalistic architecture, while still ensuring that future refactoring is not a nightmare. The GUI is generally separate from the application, and the individual windows or major components of the application do not communicate directly with one another, but rather through a central command and event system. The primary points of interest are:

- There is a single Application Service class which is not tied to the GUI or any major SDK. This is where all actions and events are consumed and produced. Future iterations may see this evolve into multiple services provided by modules. This class hides the data storage and logging from the user interfaces. This is a critical aspect to consider, as Unit Testing must be able to validate that the application always logs certain actions. (relevant for certification)
- Each window is treated like a plugin which adds a new view on the data. It does not add any new functionality! All functionality is handled by the "Application Service" class.
- Action Listeners should be limited to the main window frames, which themselves update their sub-components. This is purely for cognitive simplicity and may change if not strong enough as a tactic.
- GIS stuff (Geotools) is kept in a single package. The application should never become dependent on such a major SDK.
- GIS Layers are singletons. The application is deliberately static, and adding / removing data to and from the layers is well-defined enough that we can centralise the logic in classes which will only have one instance.

It is known that this architecture is not complete. As the application expands it will be updated to be more robust using a strong pattern. For the time being the intention is to spend some time developing the requirements of the application, before settling on a long term architecture.

# Help

If you get stuck figuring out which module to require: https://stackoverflow.com/questions/74963898/how-do-i-discover-the-correct-java-11-module-names-for-my-geotools-project
