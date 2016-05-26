package com.yubraj.core;
import java.io.*;
import java.util.Scanner;

/**
 * Created by yubraj_pokharel on 5/24/16.
 *
 * Please notice that there are only three files used in this program
 * if you want to input more than 3 mapper then please create the no. of files aslo
 * respectively. Add the files in Filepath array
 * Output can be seen the Output.txt file in the root folder if you type yes in the console
 */

public class Main {

    static String[] FilePath = { "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData.txt",
                                 "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData2.txt",
                                 "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData3.txt"
                                };

    public static void main(String[] args) throws IOException {
        Scanner sn = new Scanner(System.in);
        System.out.println("Enter the no. of mapper : ");
        int node = sn.nextInt();
        System.out.println("Enter the no. of reducer : ");
        int reducer = sn.nextInt();

        Operations o = new Operations();
        o.ask_decision();
        System.out.println("total Mapper : "+node);
        System.out.println("total Reducer : "+reducer);
        WordCount wc = new WordCount(reducer,node,FilePath);
        wc.manage_partition();
        wc.group_by_keys();
        wc.showReducedOutput();
    }




}
