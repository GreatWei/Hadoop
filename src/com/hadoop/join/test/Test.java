package com.hadoop.join.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;

public class Test {

    public  void Jion() throws Exception{

        Configuration configuration = new Configuration();
        System.setProperty("HADOOP_USER_NAME","zhang");

        //启动一个job 封装计算程序的mapper和reduce 输入和输出
        Job job1=Job.getInstance(configuration);
        Job job2=Job.getInstance(configuration);

        JobControl jobControl = new JobControl("s");
        ControlledJob controlledJob1=new ControlledJob(job1.getConfiguration());
        ControlledJob controlledJob2=new ControlledJob(job2.getConfiguration());

        //依赖关系
        controlledJob2.addDependingJob(controlledJob1);

        jobControl.addJob(controlledJob1);
        jobControl.addJob(controlledJob2);

        Thread thread = new Thread(jobControl);
        thread.start();
    }
}
