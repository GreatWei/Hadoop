package com.hadoop.mapreduce.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MyDefineSort {
    static class MyMapper extends Mapper<LongWritable, Text,FlowBean, NullWritable>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
           String infos[]= key.toString().split("\t");
           FlowBean flowBean = new FlowBean(Integer.parseInt(infos[1].trim()),Integer.parseInt(infos[2].trim()));
           context.write(flowBean,NullWritable.get());

        }
    }

    static  class  MyReducer extends Reducer<FlowBean,NullWritable,FlowBean,NullWritable>{

       /**
        *
        * 按照自定义的compareto()方法进行能分组的
        * 灭有定义分组的时候如果使用的是自带的类型 则调用的是自带类型的compareto()
        * 根据这个方法判断是否为一组
        * 定义类型：根据自定义类型中compareto方法
        *
        *
        * */
        @Override
        protected void reduce(FlowBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            for (NullWritable nullWritable: values){
                context.write(key,nullWritable.get());
            }
        }
    }

    public static void main(String args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration();
        System.setProperty("HADOOP_USER_NAME","zhang");
        configuration.set("fs.defaultFS","hdfs://localhost:9000");
        Job job=Job.getInstance(configuration);

        job.setJarByClass(MyDefineSort.class);

        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(FlowBean.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.addInputPath(job,new Path("/word/"));
        //小文件合并的写法
        //  CombineTextInputFormat.addInputPath(job,new Path(args[0]));
        //输出路劲：最终结果输出的路径,输出路劲一定不能存在  hdfs怕把原来的文件覆盖
        FileOutputFormat.setOutputPath(job,new Path("/word2/"));



        job.waitForCompletion(true);

    }
}
