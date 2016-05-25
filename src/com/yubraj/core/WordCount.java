package com.yubraj.core;

/**
 * Created by 984886 on 5/25/2016.
 */
public class WordCount {

    private int r; //no. of reducer
    private int m; //no. of mapper

    public int getPartition(String key){
        return (int) key.hashCode() % r;
    }

}
