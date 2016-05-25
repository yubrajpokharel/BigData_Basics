package com.yubraj.core;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by yubraj_pokharel on 5/24/16.
 */
public class KeyPair<k extends Comparable<k>, v> implements Comparable<KeyPair<k, v>> {

    private final k K;
    private final v V;

    public KeyPair(k k, v v) {
        K = k;
        V = v;
    }

    public k getK() {
        return K;
    }

    public v getV() {
        return V;
    }

    @Override
    public String toString() {
        return "< " +
                K +
                ", " + V +
                " >";
    }

    @Override
    public int compareTo(KeyPair<k, v> o) {
        return 0;
    }
}

