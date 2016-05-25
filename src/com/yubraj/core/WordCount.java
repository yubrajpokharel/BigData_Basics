package com.yubraj.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 984886 on 5/25/2016.
 */
public class WordCount {

    public int r; //no. of reducer
    public int m; //no. of mapper

    public String[] filePath;
    static List<List<KeyPair<String, Integer>>> kpList = new ArrayList<>();
    static List<List<Group>> groupByPair = new ArrayList<>();

    public WordCount(int r, int m, String[] filePath) throws IOException {
        this.r = r;
        this.m = m;
        this.filePath = filePath;
        suffle_sort();
    }


    private void suffle_sort() throws IOException {
        for (int i = 0; i < m; i++) {
            System.out.println("Mapper " + i + " Output");
            MapData(filePath[i], int index);
            SortMappedData(i);
            for (List<KeyPair<String, Integer>> kvp : kpList) {
                System.out.println(kvp.toString());
            }
        }
    }



    public int getPartition(String key){
        return (int) key.hashCode() % r;
    }


    public static void MapData(String filePath, int index) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] result = content.split("[-\\s]");
        for (String s : result) {
            if (s.matches("[a-zA-Z]+")) {
                kpList.get(index).add((List<KeyPair<String, Integer>>) new KeyPair<String, Integer>(s.toLowerCase(), 1));
            }
        }
    }

    public static void GroupByPair(int index) {
        for (int i = 0; i < kpList.get(index).size(); i++) {
            String currentKey = kpList.get(index).get(i).getK();
            Group gbp = new Group(currentKey);

            while (i < kpList.size() - 1 && kpList.get(index).get(i + 1).getK().equals(currentKey)) {
                gbp.addVal();
                i++;
            }
            groupByPair.get(index).add(gbp);
        }
    }

    public static void SortMappedData(int i) {
        Collections.sort(kpList.get(i), (a, b) -> a.getK().compareToIgnoreCase(b.getK()));
    }

    public static String Reducer(Group gbp) {
        return "< " + gbp.getKey() + " , " + gbp.getIndexList().stream().mapToInt(i -> ((Integer) i)).sum() + " >";
    }

}
