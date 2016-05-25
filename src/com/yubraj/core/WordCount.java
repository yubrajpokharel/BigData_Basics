package com.yubraj.core;

/**
 * Created by 984886 on 5/25/2016.
 */
public class WordCount {

    public int r; //no. of reducer
    public int m; //no. of mapper

    public String filePath;
    public KeyPair[] keyPairs;
    public Group[] groups;

    public WordCount(int r, int m, String filePath) {
        this.r = r;
        this.m = m;
        this.filePath = filePath;
    }



    public int getPartition(String key){
        return (int) key.hashCode() % r;
    }

}
