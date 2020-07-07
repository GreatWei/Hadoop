package com.hadoop.join.test;

import com.hadoop.mapreduce.test.FlowBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class ReduceJoin {

    static class MyMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable> {

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            //获取文件名
            context.getInputSplit();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {


        }
    }

    static  class  MyReducer extends Reducer<FlowBean,NullWritable,FlowBean,NullWritable> {

        /**
         *

         *
         *
         * */
        @Override
        protected void reduce(FlowBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        }
    }

}
