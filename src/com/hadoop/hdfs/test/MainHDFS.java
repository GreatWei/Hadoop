package com.hadoop.hdfs.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.net.URI;

public class MainHDFS {

    public static void main(String[] args) throws Exception {
    downloadHDFS();

    }

    public static  void  uploadHDFS() throws Exception{
        //FileSystem:这个对象时hdfs抽象目录树的一个实例
        /**
         * configuration对象：加载配置文件的对象 hadoop 集群
         *
         *      core-default.xml
         *      hdfs-default.xml  %hadoop_home%/share/hadoop/hdfs/hdfs-2.10.0.jar
         *      mapred-default.xml
         *      yarn-default.xml
         *
         *      配置文件对象加载文件是来自于jar包中hdfs-default.xml
         *      配置文件加载有顺序
         *      1. jar包中中的hdfs-default.xml
         *      2. 工程的classpath（src）下的配置文件
         *          只识别2种名字：hdfs-default.xml hdfs-site.xml
         *      3.代码设置配置文件
         *
         *      优先级：
         *      jar-》src-》代码，逐层覆盖。
         *
         * */
        //System.setProperty("HADOOP_USER_NAME","ZHANG");//程序操作的用户
        Configuration conf= new Configuration();
        conf.set("dfs.replication","5");
        //  FileSystem fs=FileSystem.get(conf);  //本地文件系统
        //   FileSystem fs=FileSystem.get(new URI("hdfs://localhost:9000"),conf);//分布式文件系统
        FileSystem fs=FileSystem.get(new URI("hdfs://localhost:9000"),conf,"zhang");//分布式文件系统
        //Path hdfs 内置对象 文件路径对象
        //     Path src = new Path("C:\\Users\\zhang\\Desktop\\ftp.txt");
        //C:\Users\zhang\Desktop\Netty权威指南 PDF电子书下载 带目录书签 完整版.pdf
        Path src = new Path("C:\\Users\\zhang\\Desktop\\idea.txt");
        Path dst = new Path("/idea5.txt");//代码运行的地址不是hdfs的地址
        /**
         *文件在上传/下载，会生成一个crc文件
         *
         *数据存储的路径：
         * current:真实数据
         *      下会有一个块池的目录，这个目录就是namenode初始化的时候生成的BP-XXXXXXXXXX
         *      所有的块信息都存储在这个目录
         *      最终数据存储的目录
         *      E:\hadoop\workplace\data\current\BP-280370242-192.168.126.1-1591772105924\current\finalized\subdir0\subdir0
         *      每个文件在上传的过程中都会生成俩个文件
         *      blk_1073741834:原始文件 blk_块的id（全局唯一的）
         *      blk_1073741834_1010.meta 原始文件的元数据的信息，用于记录原始文件长度，创建时间、偏移量信息
         *
         *      文件在下载的时候会生成一个crc结尾的文件，这个文件用于校验文件下载和上传的文件是否是同一个文件 用与校验文件的完整性
         *      文件下载的时候进行文件校验，根据文件的起始偏移量和结尾偏移量，如果中间的内容不发生改变，则校验通过，
         *          比如：末尾追加，校验通过；中间添加，则校验不通过
         *
         *in_use_lock 这个文件锁文件，作用于标识datanode进程，一个节点上只允许开启一个datanode
         *
         *
         *
         * namenode的version
         * #Thu Jun 11 23:08:34 CST 2020
         * datanodeUuid=536ce7cb-57f1-455e-b64c-bcd26b2e58ae
         * storageType=DATA_NODE
         * cTime=0
         * clusterID=CID-2b9aff8e-f0e7-43c5-b510-da20db12bbd4 集群标识
         * layoutVersion=-57
         * storageID=DS-c60b7608-e759-4e35-b416-de5d9950d619 块池id  联邦模式下不同的namenode管理数据的blockpoolID不同
         * */
        fs.copyFromLocalFile(src,dst);
        System.out.println(fs instanceof DistributedFileSystem);
    }

    public static  void  downloadHDFS() throws  Exception{
        Configuration configuration = new Configuration();
        FileSystem fs=FileSystem.get(new URI("hdfs://localhost:9000"),configuration,"zhang");//分布式文件系统
        //Path hdfs 内置对象 文件路径对象
        Path src = new Path("/idea.txt");
        Path dst = new Path("E:\\TEST.txt");

        fs.copyToLocalFile(src,dst);
    }


}
