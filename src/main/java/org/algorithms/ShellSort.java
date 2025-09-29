package org.algorithms;

import org.cli.CommandLineInterFace;

import java.util.Arrays;

public class ShellSort implements Sort {

    static class Metrics {
        long timeNs;
        long comparisons;
        long shifts;

        @Override
        public String toString() {
            return "Time = " + (timeNs / 1_000_000.0) + " ms, " +
                    "Comparisons = " + comparisons + ", " +
                    "Shifts = " + shifts;
        }
    }

    @Override
    public void sort(int count) {
        var cra = CreateRandomArray(count);
        CommandLineInterFace.CSVWriter(Arrays.toString(cra));
        System.out.println("Shell sequence:");
        Shell(cra);

        System.out.println("\nKnuth sequence:");
        Knuth(cra);

        System.out.println("\nSedgewick sequence:");
        Sedgewick(cra);
    }

    public static int[] CreateRandomArray(int count) {
        return HeapSort.CreateRandomArray(count);
    }

    // --- последовательности ---
    public static int[] shellGaps(int n) {
        int[] gaps = new int[(int) (Math.log(n) / Math.log(2))];
        int k = 0;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            gaps[k++] = gap;
        }
        return Arrays.copyOf(gaps, k);
    }

    public static int[] knuthGaps(int n) {
        int gap = 1;
        int count = 0;
        while (gap < n) {
            count++;
            gap = 3 * gap + 1;
        }
        int[] gaps = new int[count];
        gap = 1;
        for (int i = 0; i < count; i++) {
            gaps[count - i - 1] = gap;
            gap = 3 * gap + 1;
        }
        return gaps;
    }

    public static int[] sedgewickGaps(int n) {
        int[] gaps = new int[40]; // запас
        int k = 0;
        int gap;
        int i = 0;

        while (true) {
            if (i % 2 == 0) {
                gap = (int) (9 * Math.pow(4, i / 2) - 9 * Math.pow(2, i / 2) + 1);
            } else {
                gap = (int) (Math.pow(4, (i + 1) / 2) - 3 * Math.pow(2, (i + 1) / 2) + 1);
            }

            if (gap >= n) break;
            if (gap > 0) {   // <-- защита от -1 и 0
                gaps[k++] = gap;
            }
            i++;
        }
        return Arrays.copyOf(gaps, k);
    }

    public void Shell(int[] arr) {
        if (arr == null || arr.length == 0) return;
        int[] gaps = shellGaps(arr.length);
        Metrics m = new Metrics();
        arr = ShellSorter(arr, gaps, m);
        CommandLineInterFace.CSVWriter(Arrays.toString(arr));
        System.out.println(m);


    }

    public void Knuth(int[] arr) {
        if (arr == null || arr.length == 0) return;
        int[] gaps = knuthGaps(arr.length);
        Metrics m = new Metrics();
        arr = ShellSorter(arr, gaps, m);
        CommandLineInterFace.CSVWriter(Arrays.toString(arr));
        System.out.println(m);
    }

    public void Sedgewick(int[] arr) {
        if (arr == null || arr.length == 0) return;
        int[] gaps = sedgewickGaps(arr.length);
        Metrics m = new Metrics();
        arr = ShellSorter(arr, gaps, m);
        CommandLineInterFace.CSVWriter(Arrays.toString(arr));
        System.out.println(m);
    }

    // --- сортировщик с метриками ---
    public int[] ShellSorter(int[] arr, int[] gaps, Metrics m) {
        long start = System.nanoTime();

        int n = arr.length;
        for (int gap : gaps) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;

                while (j >= gap) {
                    m.comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        m.shifts++;
                        j -= gap;
                    } else {
                        break;
                    }
                }
                arr[j] = temp;
            }
        }

        m.timeNs = System.nanoTime() - start;
        return arr;
    }
}
