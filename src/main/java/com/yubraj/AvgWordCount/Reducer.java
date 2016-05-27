package com.yubraj.AvgWordCount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 984886 on 5/26/2016.
 */
public class Reducer {

    public String key;
    public ArrayList<List<Integer>> indexList;


    public Reducer(String key, List<Integer> i) {
        this.key = key;
        indexList = new ArrayList<>();
        addVal(i);
    }

    public void addVal(List<Integer> i) {
        this.indexList.add(i);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<List<Integer>> getIndexList() {
        return indexList;
    }

    public void setIndexList(ArrayList<List<Integer>> indexList) {
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
