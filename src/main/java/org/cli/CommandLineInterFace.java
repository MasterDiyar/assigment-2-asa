package org.cli;

import org.algorithms.HeapSort;
import org.algorithms.ShellSort;

import java.util.Scanner;

public class CommandLineInterFace {

    Scanner sc = new Scanner(System.in);
    String word="";
    public CommandLineInterFace() {
        while (!word.equals("exit")) {
            System.out.print("Enter 1 for Shell, 2 for Heap, exit for quiting");
            word = sc.nextLine();


            if (word.equals("1")) {
                ShellCommands();
            }
            else if (word.equals("2")) {
                HeapCommands();
            }

        }
    }

    private void ShellCommands() {
        ShellSort sorter = new ShellSort();
        System.out.println("Enter type of gaps and count of items in array");
        System.out.println("Enter 3 for shell, 4 for knuth, 5 for sedgewick");
        var enter = sc.nextLine().split(" ");
        var randArr = HeapSort.CreateRandomArray(Integer.parseInt(enter[1]));
        switch (Integer.parseInt(enter[0])) {
            case 3: sorter.Shell(randArr);break;
            case 4: sorter.Knuth(randArr);break;
            case 5: sorter.Sedgewick(randArr);break;
        }

    }
    private void HeapCommands() {
        HeapSort sorter = new HeapSort();
        System.out.println("Enter how many elements will be in array");
        int count = Integer.parseInt(sc.nextLine());
        sorter.sort(count);
    }


}
