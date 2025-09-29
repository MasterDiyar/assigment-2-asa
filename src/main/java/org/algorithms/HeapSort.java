package org.algorithms;

import org.cli.CommandLineInterFace;

import java.util.Arrays;
import java.util.Random;

public class HeapSort implements Sort {

    private int n; // heap size
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayAccesses = 0;
    private long allocations = 0;

    @Override
    public void sort(int count) {
        int[] arr = CreateRandomArray(count);
        CommandLineInterFace.CSVWriter(Arrays.toString(arr));

        System.out.println("Original array:");
        System.out.println(Arrays.toString(arr));

        resetMetrics();
        allocations++; // one array created
        sort(arr);     // delegate to array-based sort
        CommandLineInterFace.CSVWriter(Arrays.toString(arr));

        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(arr));

        System.out.println("Metrics:");
        printMetrics();
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        resetMetrics();
        n = arr.length;

        // Build heap (bottom-up)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    private void heapify(int[] arr, int heapSize, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heapSize) {
            comparisons++;
            arrayAccesses += 2;
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        if (right < heapSize) {
            comparisons++;
            arrayAccesses += 2;
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, heapSize, largest);
        }
    }

    private void swap(int[] arr, int i, int j) {
        swaps++;
        arrayAccesses += 4;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void resetMetrics() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        allocations = 0;
    }

    private void printMetrics() {
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Swaps: " + swaps);
        System.out.println("Array Accesses: " + arrayAccesses);
        System.out.println("Allocations: " + allocations);
    }

    public static int[] CreateRandomArray(int count) {
        Random random = new Random();
        int[] arr = new int[count];
        for (int i = 0; i < count; i++) {
            arr[i] = random.nextInt(count * 417);
        }
        return arr;
    }
}
