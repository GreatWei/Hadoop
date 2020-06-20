package com.hadoop.mapreduce.test;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 *
 *reduce处理的是map的结果 reduce的输入是map的输出
 *
 *
 * KEYIN ：reduce的输入的类型---mapper的输出的key的类型 Text
 * VALUEIN ： reduce的输入的value类型--mapper输出的value的类型 IntWritable
 *
 * 输出应该是reduce最终处理完的业务逻辑的输出  单词1，55    单词2，33
 * KEYOUT, reduce统计结果的key的类型 这里指的是最终统计完成的单词 Text
 * VALUEOUT, reducer统计结果的value的类型 这里指的是单词出现的总次数 IntWritable
 *
 *
 *
 *
 *
 * */


public class WordCountReducer extends Reducer<Text , IntWritable, Text, IntWritable> {

    /**
     * map端输出的结果
     * <hello,1> <hello,2>这些数据是真理合并后
     *
     * map端输出的数据到达reduce端之前就会对数据进行一个整理，这个整理是框架帮你做的，这个整理就是分组
     * 框架内部会进行一个分组，按照map输出的key进行分组 key相同的为一个组  map端输出的数据种有多少个不同的key就有多少个组
     *
     *
     * Text key,每一组中的那个相同的key
     * Iterable<IntWritable> values, 每一个组中相同key对应的所有的value值
     * Context context : 上下文对象 用于传输 写出到hdfs
     *
     *
     *
     * */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;
        for(IntWritable i:values){
            sum=sum+i.get();
        }
        context.write(key,new IntWritable(sum));
    }
}
