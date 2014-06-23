# Impressum Monitor JEE7

This application is a tool to monitor the impressum page of registered websites.
Every minute, a job is started to get the content of the page and compare it with the previously stored content.
If a change has occurred, an email will be sent to notify.

## Requirements
- JDK 1.8
- JEE 7
- Maven 3
- Widlfly 8.1.0 final


## Compile and package
````
mvn clean install
````
Deploy the generated war file to your favorite application server (I only tested with [Wildfly](http://download.jboss.org/wildfly/8.1.0.Final/wildfly-8.1.0.Final.zip)).
You'll need to create a datasource


## Notes
This application was done during a 4 hours exercise.


## TODO
- finish Integration Tests for MonitorJobRepository
- make some tests for the PropertyFactory
- make some tests for the MonitorJobRestApi (with [Rest-Assured](https://code.google.com/p/rest-assured/))
- make a nice UI to add, update or delete the stored impressum pages to monitor
- improve the [verification of page content change](https://github.com/NicolasRitouet/impressum-monitor-jee7/blob/master/src/main/java/io/github/nicolasritouet/domain/boundary/MonitorJobExecutor.java#L52)
- make a few maven profiles to build for various application servers