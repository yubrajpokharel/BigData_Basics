package com.yubraj.AvgWordCount;

/**
 * Created by 984886 on 5/26/2016.
 */
public class Mapper<k extends Comparable<k>, v> implements Comparable<Mapper<k, v>> {
    private k key;
    private v value;

    public Mapper(k key, v value) {
        this.key = key;
        this.value = value;
    }

    public k getKey() {
        return key;
    }

    public void setKey(k key) {
        this.key = key;
    }

    public v getValue() {
        return value;
    }

    public void setValue(v value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "< " + key + ", " + value +" >";
    }


    @Override
    public int compareTo(Mapper<k, v> o) {
        return 0;
    }
}
