import org.algorithms.HeapSort;
import org.algorithms.ShellSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShellSortTest {

    private ShellSort sorter;
    private Random rand;

    @BeforeEach
    void setUp() {
        sorter = new ShellSort();
        rand = new Random(812);
    }

    @Test
    @DisplayName("empty test: ")
    void testEmptyArray() {
        int[] arr = {};
        sorter.Shell(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    @DisplayName("single element:")
    void testSingleElement() {
        int[] arr = {7};
        sorter.Shell(arr);
        assertArrayEquals(new int[]{7}, arr);
    }
    @Test
    @DisplayName("duplicates")
    void testDuplicates() {
        int[] arr = {5, 5, 5, 5};
        sorter.Knuth(arr);
        assertArrayEquals(new int[]{5, 5, 5, 5}, arr);
    }@Test
    @DisplayName("reverse sorted: массив в обратном порядке")
    void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        sorter.Shell(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    @DisplayName("random array vs Arrays.sort()")
    void testRandomArrayProperty() {
        for (int n = 1; n < 100; n++) {
            int[] arr = rand.ints(n, -1000, 1000).toArray();
            int[] copy = Arrays.copyOf(arr, arr.length);

            sorter.Shell(arr);
            Arrays.sort(copy);

            assertArrayEquals(copy, arr, "ShellSort must match Java's Arrays.sort()");
        }
    }

    @Test
    @DisplayName("large array vs Arrays.sort()")
    void testCompareWithJavaSort() {
        int[] arr = rand.ints(1000, -10000, 10000).toArray();
        int[] copy = Arrays.copyOf(arr, arr.length);

        sorter.Shell(arr);
        Arrays.sort(copy);

        assertArrayEquals(copy, arr);
    }

    @Test
    @DisplayName("scalability test: производительность")
    void testScalability() {
        int[] sizes = {100, 1_000, 10_000, 50_000};
        for (int n : sizes) {
            int[] arr = rand.ints(n, -1_000_000, 1_000_000).toArray();
            long start = System.nanoTime();
            sorter.Shell(arr);
            long end = System.nanoTime();

            assertTrue(isSorted(arr));
            System.out.printf("ShellSort on n=%d took %.2f ms%n", n, (end - start) / 1e6);
        }
    }

    @Test
    @DisplayName("input distributions: разные типы входных данных")
    void testInputDistributions() {
        int n = 10_000;

        int[] randomArr = rand.ints(n, -1000, 1000).toArray();
        sorter.Shell(randomArr);
        assertTrue(isSorted(randomArr));

        int[] sortedArr = rand.ints(n, -1000, 1000).sorted().toArray();
        sorter.Shell(sortedArr);
        assertTrue(isSorted(sortedArr));

        int[] reverseArr = Arrays.stream(sortedArr).boxed()
                .sorted((a, b) -> Integer.compare(b, a))
                .mapToInt(Integer::intValue).toArray();
        sorter.Shell(reverseArr);
        assertTrue(isSorted(reverseArr));

        int[] nearlySorted = Arrays.copyOf(sortedArr, sortedArr.length);
        swap(nearlySorted, 100, 200); // small disruption
        sorter.Shell(nearlySorted);
        assertTrue(isSorted(nearlySorted));
    }

    @Test
    @DisplayName("memory usage test: проверка памяти")
    void testMemoryUsage() {
        int[] arr = rand.ints(50_000, -1000, 1000).toArray();
        Runtime runtime = Runtime.getRuntime();

        runtime.gc();
        long before = runtime.totalMemory() - runtime.freeMemory();
        sorter.Shell(arr);
        runtime.gc();
        long after = runtime.totalMemory() - runtime.freeMemory();

        System.out.printf("Memory before: %d KB, after: %d KB%n", before / 1024, after / 1024);

        assertTrue(isSorted(arr));
    }

    // ===== helpers =====
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
