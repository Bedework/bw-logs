# bw-logs [![Build Status](https://travis-ci.org/Bedework/bw-logs.svg)](https://travis-ci.org/Bedework/bw-logs)

This project provides a number of classes and methods for processing of logs for
[Bedework](https://www.apereo.org/projects/bedework).

## Requirements

1. JDK 17
2. Maven 3

## Building Locally

> mvn clean install

## Releasing

Releases of this fork are published to Maven Central via Sonatype.

To create a release, you must have:

1. Permissions to publish to the `org.bedework` groupId.
2. `gpg` installed with a published key (release artifacts are signed).

To perform a new release use the release script:

> ./bedework/build/quickstart/linux/util-scripts/release.sh <module-name> "<release-version>" "<new-version>-SNAPSHOT"

When prompted, indicate all updates are committed

For full details, see [Sonatype's documentation for using Maven to publish releases](http://central.sonatype.org/pages/apache-maven.html).

## Release Notes
### 1.0.0
* First version

### 1.0.1
* Update library versions

### 1.0.2
* Update library versions

### 1.0.3
* Update library versions

### 1.0.4
* Update library versions

### 1.1.0
* Update library versions

### 1.1.
* Update library versions
* Build bw-logs as the app for log processing. Add new script and update script to use that module
