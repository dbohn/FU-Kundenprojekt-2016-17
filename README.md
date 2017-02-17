# Kundenprojekt - WiSe 2016/2017

Der Code in diesem Repository ist das Produkt des Softwareprojekts "Kundenprojekt 1 - Webtechnologien" im Wintersemester 2016/2017 an der Freien Universität Berlin.
Die vorliegende Software wurde (in alphabetischer Reihenfolge) von

* David Bohn
* Hanna Lachnitt
* Annika Sommer
* Eike Steinert
* Friedrich Ueberreiter

entwickelt.
Der Sinn dieses Repositories ist die Archivierung und zukünftige Referenz.
Der Code (ausgenommen die HumHub-Installation im Verzeichnis humhub ohne das Modul in humhub/protected/modules/bcs, die unter der Lizenz von HumHub veröffentlicht steht) ist nach der im Projekt getroffenen Vereinbarung unter der MIT-Lizenz veröffentlicht. Nach einer englischen Version dieses Prologs folgt das originale README.

---

The code in this repository is the result of the university course "Kundenprojekt 1 - Webtechnologien", that took place at the Free University of Berlin (Freie Universität Berlin) in the winter term 2016/2017.
The software in this repository was developed by the following students, who are listed in alphabetical order:

* David Bohn
* Hanna Lachnitt
* Annika Sommer
* Eike Steinert
* Friedrich Ueberreiter

This repository is a mirror of the internal repository, that might not be available anymore in the future. To save the code for the future and as a future reference, this fork was created.
All code (except the included HumHub installation in the humhub directory, but including the module in humhub/protected/modules/bcs) is published under the MIT license.
The following is the readme, that was prepared during the course.

---

Dieses Repository beherbergt drei Komponenten:

1. Ein Docker-Setup
2. Ein Java Demonstrator
3. Eine angepasste HumHub-Installation

Diese Komponenten werden hier kurz vorgestellt.

## Automatische Installation

Die Installation wurde in Form eines Deployment-Shellskript automatisiert.
Ausgehend von einer vorhandenen Docker-Installation baut dieses Skript den Demonstrator und führt das Setup in den entsprechenden Containern aus.
Zur Ausführung genügt der Aufruf von

```
./deploy.sh
```

## Docker-Setup

Zur Erstellung einer überall identischen Entwicklungsumgebung wird eine Container-Konfiguration in Form einer docker-compose.yml-Datei vorgegeben.
Diese installiert die folgenden Komponenten:

### nginx
Bei nginx handelt es sich um einen leichtgewichtigen Webserver, der dazu genutzt wird, die Requests an Humhub anzunehmen und an humhub weiterzugeben.
Dazu wird das humhub-Verzeichnis in diesem Repository in den Container gebunden und als Root-Verzeichnis für die Seite `humhub.local` festgelegt. Der Port 80 des nginx-Containers wird an Port 8080 des Host-Systems gebunden.

### php
Um Humhub auszuführen, wird PHP benötigt, welches in einem eigenen Container ausgelagert ist.
Die Kommunikation zwischen nginx und php erfolgt über das FastCGI-Protokoll.
Um Zugriff auf die PHP-Daten zu haben, wird hier ebenfalls das humhub-Verzeichnis als Volume in den Container gebunden.
Das Setup dieses Containers wird im Dockerfile im Verzeichnis `images/php` vorgegeben und ist auf die Anforderungen von humhub zugeschnitten.

### mysql
In diesem Container läuft MariaDB, ein Fork von MySQL, der die Datenbank für Humhub bereitstellt. Es wird dabei eine Datenbank `humhub` erstellt, auf die vom Nutzer `humhub` mit dem Passwort `1234` zugegriffen werden kann.

### database
Dieser Container enthält die Postgres-Datenbank, die von dem Demonstrator genutzt werden soll, um die Dummy-Nutzer zu speichern.
Es wird dabei eine Datenbank `humhub` erstellt, auf die vom Nutzer `humhub` mit dem Passwort `1234` zugegriffen werden kann.

### demonstrator
Zur Auslieferung der Demonstrator-Anwendung, die das BCS-System repräsentiert, wird ein Wildfly Servlet-Container verwendet, der in einem eigenen Container läuft. Hierbei wird der Port 8080 auf den Port 18080 abgebildet, um einen Zugriff auf die Webseite zu ermöglichen.

Die ausgelieferten .war-Archive werden aus dem `build`-Verzeichnis übernommen. Ein neues bzw. geändertes Archiv wird sofort verfügbar gemacht.

### Kommunikation im Docker-Netz
Alle Container befinden sich in einem eigenen Netzwerk, `humhub` genannt. Dadurch ist es den Containern möglich, sich gegenseitig über den Container-Namen zu erreichen. So reicht es aus, als Host für die Datenbank lediglich `mysql` anzugeben.

## Ausführung des Docker-Setups
Eine funktionierende Docker-Installation mit docker-compose vorausgesetzt, lässt sich diese Umgebung durch den Befehl `docker-compose up -d` starten. Die Entwicklungsumgebung kann durch die Eingabe von `docker-compose down` wieder beendet werden.

## Java-Demonstrator
Bei dem Java-Demonstrator handelt es sich um eine Servlet-basierte Anwendung.
Als Servlet-Container kommt, wie oben beschrieben, derzeit JBoss Wildfly zum Einsatz.
Im Verzeichnis `Demonstrator` befindet sich ein IntelliJ-Projekt, das das nötige Setup zur Arbeit enthält.
Es kann entweder durch Doppelklick auf die Demonstrator.iml-Datei geöffnet werden oder auch durch `File | Open` direkt aus IntelliJ heraus geöffnet werden.
Zum Kompilieren des Codes muss einmalig im Panel `Maven` der Punkt `package` ausgewählt werden.
Dadurch wird automatisch in das build-Verzeichnis, das oben beschrieben wurde, das erstellte `Demonstrator.war`-Archiv abgelegt und im selben Schritt auch von Wildfly sofort geladen.
Die Anwendung ist dann unter der Adresse `http://localhost:8002/Demonstrator/` verfügbar.

Im Unterordner `web` werden die .jsp-Dateien und die web.xml (die Hauptkonfigurationsdatei) abgelegt. Unter `resources` können CSS-Dateien, Bilder oder andere Assets abgelegt werden. Der `src`-Ordner ist dann schließlich dem Java-Code vorbehalten.

## Humhub
Diese Installation von Humhub enthält alle Modifikationen.