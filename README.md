Kotlin File Processor
=====================
A Kotlin program that reads a CSV file, and then for each record, calls a REST API that performs a card payment.

## Pre-requisites
* Install latest Jetbrains Intellij IDEA (it will include Kotlin SDK)
* Install Java 8+
* Install Gradle 2.5.4

## Running applications
To launch the application, run the following:
* Open a console and run `gradle run` ... it will monitor and process csv files in the `working` sub-folder

You will see processing output on your console and in a file called `fileloader-0.log`

To run just the unit tests, execute `gradle test`

## References
Below are some reference web sites
* https://kotlinlang.org/docs/reference/
* https://www.kotlinresources.com/
* https://khttp.readthedocs.io/en/latest/
* https://www.petrikainulainen.net/programming/testing/running-kotlin-tests-with-gradle/
* https://github.com/google/gson