package com.hadoop.mapreduce.test;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * Mapper：将每个单词拆出来 并且进行打标签
 * Mapper的最终输出：<单词，1>
 * Mapper类中的四个泛型
 * 你的输入就是需要统计的文件 这个文件是一行给一次
 * 输入：一行的内容
 * KEYIN:输入key的泛型，这里指的是每一行的偏移量 mapredeuce底层文件输入以来流的方式
 * 字节流的方式 \r\n 记录的是每一行的起始变量 一般情况下没啥用 Long
 * VALUEIN:输入的值的类型 这里指的是一行的内容 String
 * <p>
 * KEYOUT:输出的key的类型 者的是 单词 string
 * VALUEOUT:输出的value的类型  指的是那个1 便于统计 Integer
 * <p>
 * <p>
 * mapreducer处理数据必然经过持久化磁盘或者网络传输，数据必须序列化 反序列化
 * <p>
 * Java中的序列话Serializable 连同类结构一起进行序列划和反序列化
 * <p>
 * Java 中的序列化和反序列过于累赘，hadoop 弃用Java中序列化Serializable这一套的序列化 反序列化的东西
 * <p>
 * hadoop 自己提供了一套自己的序列化和反序列化接口Writable 有点轻便
 * 对应8种基本数据类型和string类都帮我们实现好了Writable接口
 * <p>
 * int--IntWritable
 * long--LongWritable
 * double--DoubleWritable
 * String--Text
 * null--NullWritable
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private static final IntWritable one = new IntWritable(1);
    private Text word = new Text();
    /**
     * 这个方法的调用频率：一行调用一次
     * LongWritable key:每一行的起始偏移量
     * Text value：每一行的内容 每次读取到哪一行的内容
     * Context context 上下文对象 用于传输的
     *
     *
     * */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//
//        //获取每一行的内容  进行每个单词的切分 将每个单词加标签1
//        String line=value.toString();
//        //对这一行内容进行切分
//        StringTokenizer itr = new StringTokenizer(value.toString());
//        String[] words=line.split("\t");
//
//        for(String word:itr){
//            context.write(new Text(word),new IntWritable(1));
//        }
        StringTokenizer itr = new StringTokenizer(value.toString());

        while(itr.hasMoreTokens()) {
            this.word.set(itr.nextToken());
            context.write(this.word, one);
        }

    }
}
