package com.hadoop.mapreduce.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class WordCount {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        ArrayList<HashMap<String,Integer>> list = new ArrayList<HashMap<String,Integer>>();
       for(int i=1;i<4;i++){
           String path="word"+i+".txt";
           HashMap<String,Integer> map = new HashMap<String,Integer>();
           list.add(map);
           new ThreadWordCount(path,map,countDownLatch).start();
       }
       countDownLatch.await();
        HashMap<String,Integer> map = new HashMap<String,Integer>();
       for( HashMap<String,Integer> tmp:list){
           for(String key:tmp.keySet())
           if(!map.containsKey(key)){
               map.put(key,tmp.get(key));
           }else {
               map.put(key,map.get(key)+tmp.get(key));
           }
       }
       System.out.println(map);

    }

    public void countOnefileword(String path,HashMap<String, Integer> map) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split("\t");
            for (String word : split) {
                if (!map.containsKey(word)) {
                    map.put(word, 1);
                } else {
                    map.put(word, map.get(word) + 1);
                }
            }
        }
        //   System.out.println(map);
       // return maps;
//        for(String key:map.keySet()){
//            System.out.println(key+":"+map.get(key));
//        }
    }


}

class ThreadWordCount extends Thread {
    private String path;
    private HashMap<String, Integer> map;
    private CountDownLatch countDownLatch;

    public ThreadWordCount() {
    }

    public ThreadWordCount(String path, HashMap<String, Integer> map, CountDownLatch countDownLatch) {
        this.path = path;
        this.map = map;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            WordCount wordCount = new WordCount();
            wordCount.countOnefileword(path,map);
            countDownLatch.countDown();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
