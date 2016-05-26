package com.yubraj.AvgWordCount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by 984886 on 5/26/2016.
 */
public class Operation {
    public int r; //no. of reducer
    public int m; //no. of mapper
    public String[] filePath;

    static HashMap<Character, List<Integer>> characterListHashMap = new HashMap<>();
    static List<List<Mapper<String, HashMap<Integer, Integer>>>> kpList = new ArrayList<>();


    public Operation(int r, int m, String[] filePath) throws IOException {
        this.r = r;
        this.m = m;
        this.filePath = filePath;
        suffle_sort();
    }

    private void suffle_sort() throws IOException {
        for (int i = 0; i < m; i++) {
            System.out.println("\n--------------------------------");
            System.out.println("Mapper " + i + " Output");
            System.out.println("--------------------------------");
            MapData(filePath[i], i);
            for (Mapper<String, HashMap<Integer, Integer>> kvp : kpList.get(i)) {
                System.out.println(kvp.toString());
            }
            //SortMappedData(i);
            System.out.println("--------------------------------");
        }
    }


    public static void MapData(String filePath, int index) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        System.out.println(content);
        String[] result;
        content = content.replaceAll("\"|'", " ").toLowerCase();

        result = content.split("[-\\s]");
        Arrays.sort(result);
        System.out.println(Arrays.asList(result));

        List<Mapper<String, HashMap<Integer, Integer>>> kpListtemp = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            if (result[i].matches("[a-zA-Z]+")) {
                Character  currentKey = result[i].charAt(0);

                HashMap<Integer, Integer> integers = new HashMap<>();
                integers.put(result[i].length(), 1);
                Mapper<String, HashMap<Integer, Integer>> newKeyPair
                        = new Mapper<String, HashMap<Integer, Integer>>(currentKey.toString(), integers);


                while (i < result.length - 1 && result[i + 1].charAt(0) == currentKey) {
                    System.out.println(result[i]);
                    Map.Entry<Integer,Integer> entry=integers.entrySet().iterator().next();
                    int pre_len = entry.getKey();
                    int pre_tot = entry.getValue();
                    integers.put(pre_len + result[i].length() + pre_len, pre_tot + 1);
                    newKeyPair.setKey(currentKey.toString());
                    newKeyPair.setValue(integers);
                    i++;
                }
                kpListtemp.add(newKeyPair);
            }
        }
        kpList.add(index, kpListtemp);
    }

}
