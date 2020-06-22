package com.hadoop.mapreduce.test;


import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义类
 */
public class FlowBean implements Writable, WritableComparable<FlowBean> {

    private int downloadflow;
    private int uploadflow;
    private int sumflow;

    public FlowBean(int downloadflow, int uploadflow) {
        this.downloadflow = downloadflow;
        this.uploadflow = uploadflow;
        this.sumflow = this.downloadflow + this.uploadflow;
    }

    public FlowBean() {
        super();
    }

    //序列化的方法
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.downloadflow);
        dataOutput.writeInt(this.uploadflow);
        dataOutput.writeInt(this.sumflow);
    }


    //反序列化
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.downloadflow=dataInput.readInt();
        this.uploadflow=dataInput.readInt();
        this.sumflow=dataInput.readInt();

    }

    public int getDownloadflow() {
        return downloadflow;
    }

    public void setDownloadflow(int downloadflow) {
        this.downloadflow = downloadflow;
    }

    public int getUploadflow() {
        return uploadflow;
    }

    public void setUploadflow(int uploadflow) {
        this.uploadflow = uploadflow;
    }

    public int getSumflow() {
        return sumflow;
    }

    public void setSumflow(int sumflow) {
        this.sumflow = sumflow;
    }

    @Override
    public String toString() {
        return uploadflow + "\t" + downloadflow + "\t" + sumflow;
    }


    //map---->reducer
    @Override
    public int compareTo(FlowBean o) {

        return o.getSumflow()-this.getSumflow();
    }
}
