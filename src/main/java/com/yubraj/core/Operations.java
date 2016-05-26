package com.yubraj.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by yubraj_pokharel on 5/26/16.
 */
public class Operations {

    Scanner sn;
    public Operations() {
        sn = new Scanner(System.in);
    }

    public void ask_decision() throws FileNotFoundException {
        System.out.println("Do you want to save the output [Y/n]: ");
        String decision = sn.nextLine();
        if(decision.equalsIgnoreCase("yes")) save_output(true);
        else save_output(false);

    }


    public static void save_output(boolean option) throws FileNotFoundException {
        if(option) {
            File file = new File("output.txt"); //Your file
            FileOutputStream fos = new FileOutputStream(file);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
        }
    }



}
