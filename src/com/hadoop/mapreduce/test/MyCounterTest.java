package com.hadoop.mapreduce.test;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyCounterTest {

    static class MyMapper extends Mapper<LongWritable, Text, NullWritable, NullWritable> {
        @Override

        /**
         * 统计总的记录条数 总字段数
         *
         *
         */
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            // 获取计数器
            Counter line_counter = context.getCounter(MyCounter.LINES);

            // 对计数器进行操作 进行总行数统计
            line_counter.increment(1L);

            // 获取下一个计数器 统计总的字段数
            Counter counts = context.getCounter(MyCounter.COUNT);
            StringTokenizer itr = new StringTokenizer(value.toString());
            int i=0;
            while(itr.hasMoreTokens()) {
                itr.nextToken();
                i++;
            }

            counts.increment(i);

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // TODO Auto-generated method stub
        Configuration configuration = new Configuration();
        System.setProperty("HADOOP_USER_NAME", "zhang");
        configuration.set("fs.defaultFS", "hdfs://localhost:9000");
        Job job = Job.getInstance(configuration);

        job.setJarByClass(MyCounter.class);

        // 没有reduce的时候设置为0，默认是1
        job.setNumReduceTasks(0);

        job.setMapperClass(MyCounterTest.MyMapper.class);

        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job, new Path("/word/word.txt"));
        // 小文件合并的写法
        // CombineTextInputFormat.addInputPath(job,new Path(args[0]));
        // 输出路劲：最终结果输出的路径,输出路劲一定不能存在 hdfs怕把原来的文件覆盖
        FileOutputFormat.setOutputPath(job, new Path("/count7/"));

        job.waitForCompletion(true);
    }

}
