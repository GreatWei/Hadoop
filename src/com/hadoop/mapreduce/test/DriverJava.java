package com.hadoop.mapreduce.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

public class DriverJava {

    public static void main(String args) throws IOException {
        Configuration configuration = new Configuration();
        System.setProperty("HADOOP_USER_NAME","zhang");
        configuration.set("fs.defaultFS","hdfs://localhost:9000");
        Job job=Job.getInstance(configuration);

    }
}
