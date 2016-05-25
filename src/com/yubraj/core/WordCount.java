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
    static List<KeyPair<String, Integer>> kpList = new ArrayList<KeyPair<String, Integer>>();
    static List<Group> groupByPair = new ArrayList<Group>();

    public WordCount(int r, int m, String[] filePath) throws IOException {
        this.r = r;
        this.m = m;
        this.filePath = filePath;
        suffle_sort();
    }


    private void suffle_sort() throws IOException {
        for (int i = 0; i < m; i++) {
            System.out.println("Mapper " + i + " Output");
            MapData(filePath[i]);
            SortMappedData();
            for (KeyPair<String, Integer> kvp : kpList) {
                System.out.println(kvp.toString());
            }
        }
    }



    public int getPartition(String key){
        return (int) key.hashCode() % r;
    }


    public static void MapData(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] result = content.split("[-\\s]");
        for (String s : result) {
            if (s.matches("[a-zA-Z]+")) {
                kpList.add(new KeyPair<String, Integer>(s.toLowerCase(), 1));
            }
        }
    }

    public static void GroupByPair() {
        for (int i = 0; i < kpList.size(); i++) {
            String currentKey = kpList.get(i).getK();
            Group gbp = new Group(currentKey);

            while (i < kpList.size() - 1 && kpList.get(i + 1).getK().equals(currentKey)) {
                gbp.addVal();
                i++;
            }
            groupByPair.add(gbp);
        }
    }

    public static void SortMappedData() {
        Collections.sort(kpList, (a, b) -> a.getK().compareToIgnoreCase(b.getK()));
    }

    public static String Reducer(Group gbp) {
        return "< " + gbp.getKey() + " , " + gbp.getIndexList().stream().mapToInt(i -> ((Integer) i)).sum() + " >";
    }

}
