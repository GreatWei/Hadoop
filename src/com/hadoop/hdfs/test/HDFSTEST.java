package com.hadoop.hdfs.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

public class HDFSTEST {
    public static void main(String[] args) throws Exception {
        //   HDFSAPI();
        IOHDFS();

    }

    public static void HDFSAPI() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "zhang");//分布式文件系统


        //创建文件夹,可以创建递归文件夹
        Path path = new Path("/HDFSAPI");
        fs.mkdirs(path);

        path = new Path("/HDFSAPI/test");
        fs.mkdirs(path);

        //删除文件夹,默认情况下是递归删除的
        //  fs.delete(new Path("/HDFSAPI/test"));
        //fs.delete(new Path("/HDFSAPI/test"),false);//false :非递归，true递归
        //文件是否存在
        System.out.println(fs.exists(new Path("/HDFSAPI1")));

        fs.rename(new Path("/ftp.txt"), new Path("/rename.txt"));

        //获取指定目录下的文件列表,指定是否递归，只能获取文件列表
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), false);
        while (listFiles.hasNext()) {
            LocatedFileStatus next = listFiles.next();
            System.out.println(next);
            //返回文件的块信息 结果封装在数组中 数组的长度代表块的个数 每一个块代表数组中的一个元素
            BlockLocation[] blockLocations = next.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                //0,1110,ZHANG {起始偏移变量,结尾偏移变量,存储位置,存储位置,......}
                String[] hosts = blockLocation.getHosts();
                System.out.println(blockLocation);
            }
        }

        System.out.println("=========================================");
        //返回指定目录的文件或目录状态信息
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : listStatus) {
            System.out.println(fileStatus);
        }

        fs.close();
    }

    public static void IOHDFS() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "zhang");//分布式文件系统

        //文件上传：本地（输入流）-----》hdfs（输出流）
        //本地的输入流：普通的输入流   hdfs输出流：hdfs专用的输出流,必须指定一个文件名
        FileInputStream in = new FileInputStream(new File("C:\\Users\\zhang\\Desktop\\高级java开发工程师+张志伟简历.pdf"));

        FSDataOutputStream fsDataOutputStream = fs.create(new Path("/IOs/jianli.pdf"));
        IOUtils.copyBytes(in, fsDataOutputStream, 4096);

        //文件下载
        FSDataInputStream fsDataInputStream = fs.open(new Path("/IOs/jianli.pdf"));
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\zhang\\Desktop\\高级java.pdf"));

        IOUtils.copyBytes(fsDataInputStream,fileOutputStream,4096);
        fs.close();

    }
}
