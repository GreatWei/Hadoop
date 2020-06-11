package com.hadoop.hdfs.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.net.URI;

public class MainHDFS {

    public static void main(String[] args) throws Exception {
        //FileSystem:这个对象时hdfs抽象目录树的一个实例
        /**
         * configuration对象：加载配置文件的对象 hadoop 集群
         *
         *
         * */
        Configuration conf= new Configuration();
      //  FileSystem fs=FileSystem.get(conf);  //本地文件系统
        FileSystem fs=FileSystem.get(new URI("hdfs://localhost:9000"),conf);//分布式文件系统
        //Path hdfs 内置对象 文件路径对象
   //     Path src = new Path("C:\\Users\\zhang\\Desktop\\ftp.txt");
        //C:\Users\zhang\Desktop\Netty权威指南 PDF电子书下载 带目录书签 完整版.pdf
        Path src = new Path("C:\\Users\\zhang\\Desktop\\Netty权威指南 PDF电子书下载 带目录书签 完整版.pdf");
        Path dst = new Path("/");//代码运行的地址不是hdfs的地址
        fs.copyFromLocalFile(src,dst);
        System.out.println(fs instanceof DistributedFileSystem);

    }
}
