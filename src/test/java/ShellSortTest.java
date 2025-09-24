import org.algorithms.HeapSort;
import org.algorithms.ShellSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

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
    }

    @Test
    @DisplayName("KnuthSort: ")
    void shellSort2() {
        int[] arr = {};
        sorter.Knuth(arr);
    }
}
