package com.thinkbiganalytics.kylo.driver;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Use spark-submit to run your application
$ $SPARK_HOME/bin/spark-submit \
  --class "com.thinkbiganalytics.kylo.driver.SimpleSpark" \
  --master local\[4\] \
  --conf "spark.driver.extraJavaOptions=-Dlog4j.configuration=file:/Users/th186036/git/simple-spark/src/main/resources/log4j.properties" \
  target/simple-spark-*.jar
**/
public class SimpleSpark {
  private static final Logger logger = LoggerFactory.getLogger(SimpleSpark.class);


  public static void main(String[] args) {
    String sparkHome = System.getenv("SPARK_HOME");
    String inputFile = sparkHome + "/README.md"; // Should be some file on your system

    logger.debug("input = {}", inputFile);

    SparkSession spark = SparkSession.builder().appName("Simple Application").getOrCreate();
    Dataset<String> logData = spark.read().textFile(inputFile).cache();

    long numAs = logData.filter(s -> s.contains("a")).count();
    long numBs = logData.filter(s -> s.contains("b")).count();

    logger.info("Lines with a: " + numAs + ", lines with b: " + numBs);

    spark.stop();
  }
}
