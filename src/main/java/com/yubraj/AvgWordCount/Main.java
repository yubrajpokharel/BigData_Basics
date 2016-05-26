package com.yubraj.AvgWordCount;

import java.io.IOException;

/**
 * Created by 984886 on 5/26/2016.
 */
public class Main {

    static String[] FilePath = { "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData.txt",
            "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData2.txt",
            "/home/yubraj_pokharel/big_data_Class/BigData_Classes/testData3.txt"
    };

    public static void main(String[] args) throws IOException {
        Operation o = new Operation(4, 3, FilePath);
    }
}
