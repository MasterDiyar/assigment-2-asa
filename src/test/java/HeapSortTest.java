package src.test.java;

import org.algorithms.HeapSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    private HeapSort sorter;
    private Random rand;

    @BeforeEach
    void setUp() {
        sorter = new HeapSort();
        rand = new Random(42); // fixed seed for reproducibility
    }

    @Test
    void testEmptyArray() {
        int[] arr = {};
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSingleElement() {
        int[] arr = {7};
        sorter.sort(arr);
        assertArrayEquals(new int[]{7}, arr);
    }

    @Test
    void testDuplicates() {
        int[] arr = {5, 5, 5, 5};
        sorter.sort(arr);
        assertArrayEquals(new int[]{5, 5, 5, 5}, arr);
    }

    @Test
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testRandomArrayProperty() {
        for (int n = 1; n < 100; n++) {
            int[] arr = rand.ints(n, -1000, 1000).toArray();
            int[] copy = Arrays.copyOf(arr, arr.length);

            sorter.sort(arr);
            Arrays.sort(copy);

            assertArrayEquals(copy, arr, "HeapSort must match Java's Arrays.sort()");
        }
    }

    @Test
    void testCompareWithJavaSort() {
        int[] arr = rand.ints(1000, -10000, 10000).toArray();
        int[] copy = Arrays.copyOf(arr, arr.length);

        sorter.sort(arr);
        Arrays.sort(copy);

        assertArrayEquals(copy, arr);
    }

    @Test
    void testScalability() {
        int[] sizes = {100, 1_000, 10_000, 100_000};
        for (int n : sizes) {
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();
            long start = System.nanoTime();
            sorter.sort(arr);
            long end = System.nanoTime();

            assertTrue(isSorted(arr));
            System.out.printf("HeapSort on n=%d took %.2f ms%n", n, (end - start) / 1e6);
        }
    }

    @Test
    void testInputDistributions() {
        int n = 10_000;

        int[] randomArr = rand.ints(n, -1000, 1000).toArray();
        sorter.sort(randomArr);
        assertTrue(isSorted(randomArr));

        int[] sortedArr = rand.ints(n, -1000, 1000).sorted().toArray();
        sorter.sort(sortedArr);
        assertTrue(isSorted(sortedArr));

        int[] reverseArr = Arrays.stream(sortedArr).boxed()
                .sorted((a, b) -> Integer.compare(b, a))
                .mapToInt(Integer::intValue).toArray();
        sorter.sort(reverseArr);
        assertTrue(isSorted(reverseArr));

        int[] nearlySorted = Arrays.copyOf(sortedArr, sortedArr.length);
        swap(nearlySorted, 100, 200); // small disruption
        sorter.sort(nearlySorted);
        assertTrue(isSorted(nearlySorted));
    }

    @Test
    void testMemoryUsage() {
        int[] arr = rand.ints(50_000, -1000, 1000).toArray();
        Runtime runtime = Runtime.getRuntime();

        runtime.gc();
        long before = runtime.totalMemory() - runtime.freeMemory();
        sorter.sort(arr);
        runtime.gc();
        long after = runtime.totalMemory() - runtime.freeMemory();

        System.out.printf("Memory before: %d KB, after: %d KB%n", before / 1024, after / 1024);

        assertTrue(isSorted(arr));
    }

    private boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

