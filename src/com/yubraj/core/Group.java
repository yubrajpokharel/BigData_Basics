package com.yubraj.core;

import java.util.ArrayList;

/**
 * Created by yubraj_pokharel on 5/25/16.
 */
public class Group {
    public String key;
    public ArrayList<Integer> indexList;

    public Group(String key) {
        this.key = key;
        indexList = new ArrayList<Integer>();
        addVal();
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