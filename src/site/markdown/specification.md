# Lastenheft

## Verwandte Dokumente

- DIN 69901, Projektmanagement
- DIN 69901-5, Begriffe der Projektabwicklung
- IEEE 830-1998: Software Requirements Specification, Lastenhefte für Softwaresysteme

## Acronyms

|||
|---|---|
|PC|Personal Computer|
|THW|Technischeshilfswerk|
|ZTr.|Zugtrupp|

## Einführung

This document describes the desired

* A high level requirement
    > High level Requirement comment 
    - A low level requirement, detailing the parent high level requirement
        > Low level Requirement comment

## Beschreibung des Istzustands

The ZTr. are officially paper based. Documentation during Einsätze is to be documented by hand, and entered into THWin post Einsatz (reference)

### Software

There is a rich collection of software available online, and a very strong Open Source community within the German BOS. Unfortunately, there is a lack of offline software "hardened" for emergency usage in catastrophensituationen (e.g. no network).

### Identität

In the IT world, two things are managed: Users, and Devices. It is normal in any large organisation that a Server will exist with a database of Users and Devices which belong to any given organisation.

Modern PC setup, expecially under windows and Apple, have tried to further force this concept of Identity with their account systems. Windows Hello is a prime example

At current, the THW ZTr. Laptops are all Sonderbeschaffungen. They are not centrally bough or managed, and as such there is no Identity system which can be used to confirm the identity of a user.

## Beschreibung des Soll-Konzepts

The ZTr. must be digitized. Notes must be taken on a digital device, maps plans and instructions must be created and managed electronically, while at the same time providing the same speed and safety that a pen and pencil may provide. All of this while not being connected to any network (neither internet nor WLAN)

In the event that additional network services are available, then the ZTr. must be able to benefit from them in addition to the core features. For example, if internet is available, then files must be sendable via Hermine.

The document artefacts as they are used in the THW must remain present. For example, it is mandatory that the information can be represented in the form of the Fb Fü 2 document. The reason for this is the interoperability with systems and processes. Any solution **may** offer better solutions to digitalization, but **must** provide for the existing document format.

Note, that there is no requirement that the information is immediately available everywhere! The use of "cool features" may **not** come at the cost of the ability to effectively operate the software offline.

## Beschreibung von Schnittstellen

- GUI for human use
- Possibility of extending with other interfaces such as HTTP
- Export of the data in existing document layouts as PDF files

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
