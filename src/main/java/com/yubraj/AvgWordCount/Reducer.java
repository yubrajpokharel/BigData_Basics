package com.yubraj.AvgWordCount;

import java.util.ArrayList;

/**
 * Created by 984886 on 5/26/2016.
 */
public class Reducer {

    public String key;
    public ArrayList<Integer> indexList;

    public Reducer(String key) {
        this.key = key;
        indexList = new ArrayList<Integer>();
        addVal();
    }

    public Reducer(String key, int i) {
        this.key = key;
        indexList = new ArrayList<Integer>();
        addVal(i);
    }

    public void addVal(int i) {
        this.indexList.add(i);
    }

    public void addVal() {
        this.indexList.add(1);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Integer> getIndexList() {
        return indexList;
    }

    public void setIndexList(ArrayList<Integer> indexList) {
        this.indexList = indexList;
    }

    @Override
    public String toString() {
        return "< " +
                key +
                ", " + indexList.toString() +
                " >";
    }

}
