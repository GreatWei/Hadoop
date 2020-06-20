package com.hadoop.mapreduce.test;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 驱动类：
 *
 * mapreduce中一个计算程序叫做job
 *
 *
 * */
public class Dirver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration configuration = new Configuration();
        System.setProperty("HADOOP_USER_NAME","zhang");

        //启动一个job 封装计算程序的mapper和reduce 输入和输出
        Job job=Job.getInstance(configuration);



        //设置的是计算程序的主驱动类 运行的时候打成jar包运行
        job.setJarByClass(Dirver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        //合并小文件,在进行文件maptask划分
        job.setInputFormatClass(CombineTextInputFormat.class);
      //  CombineTextInputFormat.setMaxInputSplitSize(job,10*1024*1024);//10m

        //2.设置mapper的输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //设置reducer的输出类型 代码在运行的时候泛型会被自动擦除 所以我们这里需要指定
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setNumReduceTasks(2);//reducetask个数
        //设置自定义分区规则
        job.setPartitionerClass(MyPartition.class);

      //  job.set
        //单位字节
     //   FileInputFormat.setMaxInputSplitSize(job,330);

        //默认设置输入路劲和输出路劲
       // FileInputFormat.addInputPath(job,new Path(args[0]));
        FileInputFormat.addInputPath(job,new Path("hdfs://localhost:9000/word/"));
        //小文件合并的写法
      //  CombineTextInputFormat.addInputPath(job,new Path(args[0]));

        //输出路劲：最终结果输出的路径,输出路劲一定不能存在  hdfs怕把原来的文件覆盖
        FileOutputFormat.setOutputPath(job,new Path("hdfs://localhost:9000/word2/"));



        //石是打印执行日志
        job.waitForCompletion(true);

        /**
         * 打成jar包执行
         *  hadoop jar E:\wordcount.jar com.hadoop.mapreduce.test.Dirver /word/word.txt /wordcount1/
         *
         * */
    }

}
