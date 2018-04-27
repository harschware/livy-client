#!/bin/bash

java -cp /home/harsch/.m2/repository/org/apache/spark/spark-core_2.10/1.6.2/spark-core_2.10-1.6.2.jar:\
/home/harsch/.m2/repository/org/apache/livy/livy-api/0.5.0-incubating/livy-api-0.5.0-incubating.jar:\
/home/harsch/.m2/repository/org/apache/livy/livy-client-http/0.5.0-incubating/livy-client-http-0.5.0-incubating.jar:\
/home/harsch/git/livy-client/target/livy-client-0.1.0-SNAPSHOT.jar:\
/home/harsch/.m2/repository/org/slf4j/slf4j-api/1.7.10/slf4j-api-1.7.10.jar:\
/home/harsch/.m2/repository/org/slf4j/slf4j-log4j12/1.7.10/slf4j-log4j12-1.7.10.jar:\
/home/harsch/.m2/repository/log4j/log4j/1.2.17/log4j-1.2.17.jar:\
/home/harsch/git/livy-client/target/livy-client-app-0.1.0-SNAPSHOT.jar \
com.thinkbiganalytics.kylo.driver.LivyClientApp http://localhost:8998 2

