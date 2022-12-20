[![Download Frederick](https://img.shields.io/sourceforge/dt/frederick.svg)](https://sourceforge.net/projects/frederick/files/latest/download)

[![Download Frederick](https://a.fsdn.com/con/app/sf-download-button)](https://sourceforge.net/projects/frederick/files/latest/download)

# Project Type

This project is intended to be a "pure maven" project. In order to reduce complexity we will not include any IDE specific configuration files. Instead a few hints may be provided in this readme file.

# Project Aims

We aim to provide a set of tools to help with analysing various Lotto results. The project is intended to be purely scientific in nature, and intends to foster understanding and investigating probabiity. Thanks to the large dataset available, the Lotto is a great use case as it is possible to simulate the effective accuracy over the entire dataset.

# Architechture

The intention is to provide a set of usefull interfaces and base classes, as well as a kind of "engine".

# Site Deployment

Make sure the username and password are set in the settings.xml file using the <servers> element with the site id as you would for any other repository.

```xml
<server>
  <id>frederick.sourceforge.io</id>
  <username>turnertech</username>
  <password>...</password>
</server>
```