package com.yubraj.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.yubraj.core.KeyPair;

/**
 * Created by yubraj_pokharel on 5/24/16.
 */
public class Main {
    static List<KeyPair<String, Integer>> kpList = new ArrayList<KeyPair<String, Integer>>();
    static List<Group> groupByPair = new ArrayList<Group>();
    static String FilePath = "c:\\testData.txt";

    public static void main(String[] args) throws IOException {

        System.out.println("\n Initial Key Value Pair ");
        MapData(FilePath);
        SortMappedData();
        for (KeyPair<String, Integer> kvp : kpList) {
            System.out.println(kvp.toString());
        }

        System.out.println("\n Input for the Reducer");
        GroupByPair();
        for (Group gbp : groupByPair) {
            System.out.println(gbp.toString());
        }

        System.out.println("\n Reducer Output");
        for (Group gbp : groupByPair) {
            System.out.println(Reducer(gbp));
        }
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
