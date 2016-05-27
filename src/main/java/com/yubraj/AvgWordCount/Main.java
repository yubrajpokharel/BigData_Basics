package com.yubraj.AvgWordCount;

import java.io.IOException;

/**
 * Created by 984886 on 5/26/2016.
 */
public class Main {

    public Main() {}

    static String[] FilePath = { "/home/yubraj_pokharel/IdeaProjects/BigData_Basics/src/main/testData.txt",
            "/home/yubraj_pokharel/IdeaProjects/BigData_Basics/src/main/testData2.txt",
            "/home/yubraj_pokharel/IdeaProjects/BigData_Basics/src/main/testData3.txt",
            "/home/yubraj_pokharel/IdeaProjects/BigData_Basics/src/main/testData4.txt"

            
    };

    public static void main(String[] args) throws IOException {
        Operation o = new Operation(3, 4, FilePath);
        o.manage_partition();
        o.group_by_keys();
        o.showReducedOutput();

    }
}
