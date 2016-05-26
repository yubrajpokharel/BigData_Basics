package com.yubraj.core;
import java.io.IOException;
/**
 * Created by yubraj_pokharel on 5/24/16.
 */
public class Main {


    static String[] FilePath = { "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData.txt",
                                 "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData2.txt",
                                 "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData3.txt"
                                };

    public static void main(String[] args) throws IOException {
        WordCount wc = new WordCount(4,3,FilePath);
        wc.manage_partition();
        wc.group_by_keys();
        wc.showReducedOutput();
    }


}
