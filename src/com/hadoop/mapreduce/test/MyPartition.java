package com.hadoop.mapreduce.test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;


public class MyPartition  extends Partitioner<Text, IntWritable> {

    @Override
    public int getPartition(Text key, IntWritable value, int numReduceTasks) {
        String k=key.toString();

        return (key.hashCode() & 2147483647) % numReduceTasks;
    }
}
