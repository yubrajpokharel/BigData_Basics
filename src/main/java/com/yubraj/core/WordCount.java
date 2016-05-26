package com.yubraj.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by 984886 on 5/25/2016.
 */
public class WordCount {

    public int r; //no. of reducer
    public int m; //no. of mapper

    public String[] filePath;
    static List<List<KeyPair<String, Integer>>> kpList = new ArrayList<>();
    public static Multimap<Integer, KeyPair<String, Integer>> groups = ArrayListMultimap.create();
    static List<KeyPair<String, Integer>> mapped_list = new ArrayList<KeyPair<String, Integer>>();
    static List<List<Group>> final_pair = new ArrayList<>();


    public WordCount(int r, int m, String[] filePath) throws IOException {
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
            for (KeyPair<String, Integer> kvp : kpList.get(i)) {
                System.out.println(kvp.toString());
            }
            SortMappedData(i);
            System.out.println("--------------------------------");
        }
    }

    public void manage_partition() {

        for (int mid = 0; mid < m; mid++) {
            for(int rid = 0; rid < r; rid++){
                //List<KeyPair<String, Integer>> groupList = new ArrayList<>();
                System.out.println("From map "+mid+" to reducer "+rid);
                for(KeyPair<String, Integer> kp : kpList.get(mid)){
                    if(rid == getPartition(kp.getK())){
                        groups.put(rid, kp);
                        System.out.println(kp);
                    }
                }
            }
        }
    }

    public void group_by_keys(){
        List<List<KeyPair<String, Integer>>> list = new ArrayList<>();
        for (Integer key : groups.keySet()) {
            List<KeyPair<String , Integer>> list2 = (List<KeyPair<String, Integer>>) groups.get(key);
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

    public void showReducedOutput(){
        System.out.println("\n\n");
        for(int i = 0; i<final_pair.size(); i++){
            System.out.println("--------------------------------");
            System.out.println("Reducer "+i+ " output");
            System.out.println("--------------------------------");
            for(Group g: final_pair.get(i)){
                System.out.println(Reducer(g));
            }
        }
    }


    public int getPartition(String key){
        return (int) key.hashCode() % r;
    }


    public static void MapData(String filePath, int index) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] result;
        content = content.replaceAll("\"|\'", " ");
        result = content.split("[-\\s]");

        List<KeyPair<String, Integer>> kpListtemp = new ArrayList<>();
        for (String s : result) {
            if (s.matches("[a-zA-Z]+")) {
                KeyPair<String, Integer> newKeyPair = new KeyPair<>(s.toLowerCase(), 1);
                kpListtemp.add(newKeyPair);
            }
        }
        kpList.add(index, kpListtemp);
    }

    public static void GroupByPair(List<KeyPair<String, Integer>> list) {
        Collections.sort(list, (l1, l2) -> l1.getK().compareTo(l2.getK()) );
        List<Group> groupByPair = new ArrayList<Group>();

        for (int i = 0; i < list.size(); i++) {
            String currentKey = list.get(i).getK();
            Group gbp = new Group(currentKey);

            while (i < list.size() - 1 && list.get(i + 1).getK().equals(currentKey)) {
                gbp.addVal();
                i++;
            }
            groupByPair.add(gbp);
        }
        final_pair.add(groupByPair);
        groupByPair.forEach(System.out::println);

    }


    public static void SortMappedData(int i) {
        Collections.sort(kpList.get(i), (a, b) -> a.getK().compareToIgnoreCase(b.getK()));
    }

    public static String Reducer(Group gbp) {
        return "< " + gbp.getKey() + " , " + gbp.getIndexList().stream().mapToInt(i -> ((Integer) i)).sum() + " >";
    }

}
