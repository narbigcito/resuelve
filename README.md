# Resuelve

### Requirements

* JDK 8
* Maven 3.0 or later

### Build

You can build the project using the following:

`mvn install`

A "Target" directory has been created, inside there is a Jar file named /Resuelve-1.0-SNAPSHOT.jar that is the executable file.

### Usage

The application needs 3 parameters, the id for which you want to count bills, the date from when you want to start counting and the dates to where you want to finish counting.

The dates must be in the following format: `yyyy-mm-dd`

Example:

`java -jar target/Resuelve-1.0-SNAPSHOT.jar a55f50b5-886b-4846-95c2-c3ac4cc250a7 2017-01-01 2017-06-30`

### Run tests

to execute the tests the following command must be used:

`mvn test`
