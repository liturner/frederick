# Lastenheft

## Verwandte Dokumente

|Id|Name|
|---|---|
|DIN 69901|Projektmanagement|
|DIN 69901-5|Begriffe der Projektabwicklung|
|IEEE 830-1998|Software Requirements Specification, Lastenhefte für Softwaresysteme|
|AGEOP-5|ARC STANDARD RASTER PRODUCT (ASRP)|
|AGEOP-17|SCOPE AND PRESENTATION OF MILITARY GEOGRAPHIC INFORMATION AND DOCUMENTATION (MGID)|
|AGeoP-21|GEODETIC DATUMS, PROJECTIONS, GRIDS AND GRID REFERENCES|
|ADATP-03|(RESTRICTED) NATO MESSAGE TEXT FORMATTING SYSTEM (FORMETS) - CONCEPT OF FORMETS (CONFORMETS)|

## STANAGs

|Id|Name|Verwandte Dokumente|
|---|---|---|
|STANAG 2211|GEODETIC DATUMS, PROJECTIONS, GRIDS, AND GRID REFERENCES|AGeoP-21
|STANAG 2215|Evaluation of Land Maps, Aeronautical Charts and Digital Topographic Data||
|STANAG 2251|SCOPE AND PRESENTATION OF MILITARY GEOGRAPHIC INFORMATION AND DOCUMENTATION (MGID)|AGEOP-17|
|STANAG 3600|Topographical Land Maps and Aeronautical Charts 1:250,000 for Joint Operations||
|STANAG 3666|MAXIMUM SIZES FOR MAPS, AERONAUTICAL CHARTS AND OTHER GEOSPATIAL PRODUCTS (EXCLUDING NAUTICAL CHARTS)||
|STANAG 3676|Marginal Information on Land Maps, Aeronautical Charts and Photomaps||
|STANAG 4278|Method of Expressing Navigation Accuracies||
|STANAG 4586|STANDARD INTERFACES OF UA CONTROL SYSTEM (UCS) FOR NATO UA INTEROPERABILITY|ADATP-03|
|STANAG 7044|Functional Aspects of Mission Planning Station Interface Design|AGEOP-5|
|STANAG 7074|Digital Geographic Information Exchange Standard (Digest)||

## Akronyme

|---|---|
|GUI|Graphical User Interface|
|PC|Personal Computer|
|THW|Technisches Hilfswerk|
|ZTr.|Zugtrupp|

## Einführung

This document describes the existing situation, the limitations experienced and finally a desired future.

* A high level requirement
    > High level Requirement comment 
    - A low level requirement, detailing the parent high level requirement
        > Low level Requirement comment

## Scope

This document and the project related to it is limited to the execution of Einsätze within the THW. Other organistions related to the process "Einsatz" will only be considered in terms of Interfaces.

## Beschreibung des Istzustands

The ZTr. are officially paper based. Documentation during Einsätze is to be documented by hand, and entered into THWin post Einsatz (locate reference). The execution of the process "Einsatz" is a focus of digitalisation projects

### Software

There is a rich collection of software available online, and a very strong Open Source community within the German BOS. Unfortunately, there is a lack of offline software "hardened" for emergency usage in catastrophensituationen (e.g. no network).

Furthermore, many of the existing software elements do not work towards national or international standards.

### Identität

In the IT world, two things are managed: Users, and Devices. It is normal in any large organisation that a Server will exist with a database of Users and Devices which belong to any given organisation.

Modern PC setup, expecially under windows and Apple, have tried to further force this concept of Identity with their account systems. Windows Hello is a prime example

At current, the THW ZTr. Laptops are all Sonderbeschaffungen. They are not centrally bough or managed, and as such there is no Identity system which can be used to confirm the identity of a user.

## Beschreibung des Soll-Konzepts

The ZTr. must be digitized. Notes must be taken on a digital device, maps plans and instructions must be created and managed electronically, while at the same time providing the same speed and safety that a pen and pencil may provide. All of this while not being connected to any network (neither internet nor WLAN)

In the event that additional network services are available, then the ZTr. must be able to benefit from them in addition to the core features. For example, if internet is available, then files must be sendable via Hermine.

The document artefacts as they are used in the THW must remain present. For example, it is mandatory that the information can be represented in the form of the Fb Fü 2 document. The reason for this is the interoperability with systems and processes. Any solution **may** offer better solutions to digitalization, but **must** provide for the existing document format.

Note, that there is no requirement that the information is immediately available everywhere! The use of "cool features" may **not** come at the cost of the ability to effectively operate the software offline.

The solution is to focus on standards. The priority of implementation decisions is to be prioritised as follows

1. German National Standards (DIN)
2. Internaltional Standards (ISO)
3. NATO Standardisation Agreements (STANAG)

Note, that DIN is prefered over ISO and STANAG. As mentioned, this document aims to focus on the Deutsche THW ZTr. It is preferred that the system remains interoperable with German users. As an example, the DIN for display of units (e.g. meters) will be preferred over the ISO, as a user in the THW will most likely use excell in the German region (comma vs period). That said, furhter adherance to ISO and STANAG beyond the DIN series helps to ensure international collaboration foor units like SEEBA and SEEWA.

## Beschreibung von Schnittstellen

* The System must provide a GUI optimised for Human interaction
    - The GUI must support multiple screens
        > The ZTr. can profit from information radiators, and some vehichles are now multi-screen fähig (ONEB ZTr.)
    - The GUI must be "Touch Friendly"
* The System must provide the functionality to export the Einsatz information to PDF
    > This is a long life, accessible format suitable for archiving

TODO: Possibility of extending with other interfaces such as HTTP
TODO: Think provided and consumed interfaces
TODO: Remember, file formats are interfaces too! Being able to read e.g. GeoTIFF over WMS is an interface as much as importing a CSV

## Funktionale Anforderungen

* The System must provide the functionality to write a Deployment Diary (Einsatztagebuch)
* The System must provide the functionality to display a Deployment Map
    - The Deployment Map must display a UTMRef graticule
    - The Deployment Map must display a WGS84 graticule
    - The Deployment Map must display coordinates relative to the Bullseye, if defined
* The System must provide the functionality to define a Deployment Bullseye
* The System must provide the functionality to Print a Deployment Report
* The System must provide the functionality to Import & Export a Deployment to & from a system file
* The System must provide the functionality to work in English or German
* The System must provide Grid References according to AGeoP-21
    - Coordinates must be reference with the CRS WGS 84
        > (EPSG:4326)
    - Coordinates must be displayed using UTMRef (MGRS)
        > Zone 32N (EPSG:32632)
    - Coordinates must be stored as Decimal Degrees

## Nichtfunktionale Anforderungen

### Benutzbarkeit

* The System must provide the desired functionality for Windows 10 x64 or higher
* The System must provide GUIs and NUIs which follow a "least clicks" proncipal
    > This should not be overthought. The point is, during deployment we need to be focused on doinf the job, and not on navigating complex menus.

### Zuverlässigkeit

* The System must provide the desired functionality without internet
* The System must prevent data loss in the event of unplanned application closure

### Effizienz



### Änderbarkeit

* The System must prevent modification and customisation where possible
    > This is for many reasons. Software stbility and simplicity, speed of learning and adoption from users, simplified manuals and overall security!

### Übertragbarkeit

* The System must provide communication via STANAG methods where applicable.
* The System must provide export to existing THW document layouts (e.g. Fb Fü 2).

### Wartbarkeit

## Risikoakzeptanz

There is little to no risk acceptance. This software is to be used in deployment scenarion, and in extreme cases the information captured over the user interface may be called upon for legal preceedings.

## Skizze des Entwicklungszyklus und der Systemarchitektur

- Central Data Model
-- Export and data transfer can access the data model for e.g. printing
-- Application Layer controls modification
--- GUI Input accesses application layer

## Lieferumfang

This project ecpects several deliverables:

* A template for projects, requirements and processes which can be accepted by BOS organisations when produing software tools.
    > Answer the quesion, what do I need to get my software to be the official tool for...
* Several re-usable java modules containing the more abstract and re-usable features, available on maven central
    > This is an Open Source project. The most value is gained from sharing where possible!
* A stable, simple, not-setup required tool in binary format

## Abnahmekriterien
