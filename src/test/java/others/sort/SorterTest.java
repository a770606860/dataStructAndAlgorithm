package others.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by 77060 on 2016/12/16.
 */
public class SorterTest {

    @Test
    public void testCountSort() throws Exception {
        int[] list = {1, 8,3,6,3,4,5};
        int[] list2 = {1, 3,3,4,5,6,8};
        Sorter.countSort(list, 8);
        assertThat(list).isEqualTo(list2);
    }

    @Test
    public void testMergeSort() throws Exception {
        int[] list = {1, 8,3,6,3,4,5};
        int[] list2 = {1, 3,3,4,5,6,8};
        Sorter.mergeSort(list, 0, list.length);
        assertThat(list).isEqualTo(list2);
    }
    @Test
    public void testCountSortR() throws Exception {
        List<Integer> list = Arrays.asList(8, 2, 3, 6,5,4);
        List<Integer> list2 = Arrays.asList(2,3,4,5,6,8);

        Sorter.countSort(list, 8);
        assertThat(list).isEqualTo(list2);

    }
}