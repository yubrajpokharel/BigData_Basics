package com.yubraj.AvgWordCount;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Floats;

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

    static List<List<Mapper<String, List<Integer>>>> kpList = new ArrayList<>();
    static List<List<Reducer>> final_pair = new ArrayList<>();
    public static Multimap<Integer, Mapper<String, List<Integer>>> groups = ArrayListMultimap.create();



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
            for (Mapper<String, List<Integer>> kvp : kpList.get(i)) {
                System.out.println(kvp.toString());
            }

            System.out.println("--------------------------------");
        }
    }


    public static void MapData(String filePath, int index) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        System.out.println(content);
        String[] result;
        content = content.replaceAll("\"|'|\\.", " ").toLowerCase();
        result = content.split("[-\\s]");

        Arrays.sort(result);
        List<Mapper<String, List<Integer>>> kpListtemp = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            if (result[i].matches("[a-zA-Z]+")) {
                Character  currentKey = result[i].charAt(0);

                List<Integer> integers = new ArrayList<>();
                Mapper<String, List<Integer>> newKeyPair
                        = new Mapper<String, List<Integer>>(currentKey.toString(), integers);

                int initial_NoOfChars = result[i].length();
                int initial_NoOfWords = 1;

                while (i < result.length - 1 && result[i + 1].charAt(0) == currentKey) {
                    initial_NoOfChars +=result[i+1].length();
                    initial_NoOfWords++;
                    i++;
                }
                integers.add(initial_NoOfChars);
                integers.add(initial_NoOfWords);
                newKeyPair.setValue(integers);
                kpListtemp.add(newKeyPair);
            }
        }
        kpList.add(index, kpListtemp);
    }

    public void manage_partition() {

        for (int mid = 0; mid < m; mid++) {
            for(int rid = 0; rid < r; rid++){
                System.out.println("From map "+mid+" to reducer "+rid);
                for(Mapper<String, List<Integer>> kp : kpList.get(mid)){
                    if(rid == getPartition(kp.getKey())){
                        groups.put(rid, kp);
                        System.out.println(kp);
                    }
                }
            }
        }
    }

    public void group_by_keys(){
        List<List<Mapper<String, List<Integer>>>> list = new ArrayList<>();
        for (Integer key : groups.keySet()) {
            List<Mapper<String, List<Integer>>> list2 = (List<Mapper<String, List<Integer>>>) groups.get(key);
            list.add(list2);
        }
        System.out.println("\n\n");
        for(int i = 0; i<list.size(); i++){
            System.out.println("--------------------------------");
            System.out.println("Reducer Input "+ i);
            System.out.println("--------------------------------");
            GroupByPair(list.get(i));
        }
    }

    public int getPartition(String key){
        return (int) key.hashCode() % r;
    }


    public static void GroupByPair(List<Mapper<String, List<Integer>>> list) {
        Collections.sort(list, (l1, l2) -> l1.getKey().compareTo(l2.getKey()) );
        List<Reducer> groupByPair = new ArrayList<Reducer>();
        /*System.out.println(list.toString());*/
        for (int i = 0; i < list.size(); i++) {
            String currentKey = list.get(i).getKey();
            Reducer gbp = new Reducer(currentKey, list.get(i).getValue());

/*if(currentKey.equals("a")){
    System.out.println("**********************************");
    System.out.println(gbp.toString());
}*/
            while (i < list.size() - 1 && list.get(i + 1).getKey().equals(currentKey)) {
                gbp.addVal(list.get(i+1).getValue());
                i++;
            }
/*
if(currentKey.equals("a")){
    System.out.println(gbp.toString());
    System.out.println("**********************************");
}
*/
            groupByPair.add(gbp);
        }
        final_pair.add(groupByPair);
        groupByPair.forEach(System.out::println);
    }

    public void showReducedOutput(){
        System.out.println("\n\n");
        for(int i = 0; i<final_pair.size(); i++){
            System.out.println("--------------------------------");
            System.out.println("Reducer "+i+ " output");
            System.out.println("--------------------------------");
            for(Reducer g: final_pair.get(i)){
                System.out.println(Reduced_Output(g));
            }
        }
    }

    public static String Reduced_Output(Reducer gbp) {

        double total_chars = 0;
        double total_words = 0;
        for(List<Integer> list : gbp.getIndexList()){

            total_chars += list.get(0);
            total_words += list.get(list.size()-1);
        }

        double average = total_chars/total_words;
        return "< "+gbp.getKey()+" , "+average+">";
    }

}
