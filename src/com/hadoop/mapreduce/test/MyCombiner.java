package com.hadoop.mapreduce.test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MyCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {
    //只对一个切片的（maptask）的数据进行统计
    /**
     * key：map输出的key
     * values：map输出的相同的key的所有的value
     *
     *
     * */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

    }
}
