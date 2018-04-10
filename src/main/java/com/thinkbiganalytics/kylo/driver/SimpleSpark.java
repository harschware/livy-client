package com.thinkbiganalytics.kylo.driver;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
/**
* Use spark-submit to run your application
$ $SPARK_HOME/bin/spark-submit \
  --class "com.thinkbiganalytics.kylo.driver.SimpleSpark" \
  --master local\[4\] \
  target/simple-spark-*.jar
**/
public class SimpleSpark {
  public static void main(String[] args) {
    String sparkHome = System.getenv("SPARK_HOME");
    String logFile = sparkHome + "/README.md"; // Should be some file on your system
    SparkSession spark = SparkSession.builder().appName("Simple Application").getOrCreate();
    Dataset<String> logData = spark.read().textFile(logFile).cache();

    long numAs = logData.filter(s -> s.contains("a")).count();
    long numBs = logData.filter(s -> s.contains("b")).count();

    System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

    spark.stop();
  }
}
