package tools;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
/**
 * Created by 77060 on 2017/3/5.
 */
public class ArrayToolsTest {

    @Test
    public void testBinarySearchFirstOccur() throws Exception {
        Integer[] list = new Integer[]{3, 4, 4, 5, 8, 9};

        assertThat(ArrayTools.binarySearchFirstOccur(list, 0, list.length, 2)).isEqualTo(-1);
        assertThat(ArrayTools.binarySearchFirstOccur(list, 2, list.length, 4)).isEqualTo(2);
        assertThat(ArrayTools.binarySearchFirstOccur(list, 2, list.length, 3)).isEqualTo(-3);
        assertThat(ArrayTools.binarySearchFirstOccur(list, 0, list.length, 4)).isEqualTo(1);
    }

    @Test
    public void testBinarySearchFirstOccur1() throws Exception {
        Integer[] list = new Integer[]{3, 3, 7, 8, 10};
        assertThat(ArrayTools.binarySearchFirstOccur(list, 0, list.length, 3, Integer::compareTo)).isEqualTo(0);
        assertThat(ArrayTools.binarySearchFirstOccur(list, 2, 2, 4, Integer::compareTo)).isEqualTo(-3);
        assertThat(ArrayTools.binarySearchFirstOccur(list, 2, list.length, 3, Integer::compareTo)).isEqualTo(-3);
        assertThat(ArrayTools.binarySearchFirstOccur(list, 0, list.length, 4, Integer::compareTo)).isEqualTo(-3);

    }

    @Test
    public void testBinarySearchLastOccur() throws Exception {
        Integer[] list = new Integer[]{3, 3, 7, 8, 10};

        assertThat(ArrayTools.binarySearchLastOccur(list, 0, list.length, 3)).isEqualTo(1);
        assertThat(ArrayTools.binarySearchLastOccur(list, 0, list.length, 11)).isEqualTo(-6);
        assertThat(ArrayTools.binarySearchLastOccur(list, 0, 0, 3)).isEqualTo(-1);
        assertThat(ArrayTools.binarySearchLastOccur(list, 2, 4, 8)).isEqualTo(3);
    }

    @Test
    public void testBinarySearchLastOccur1() throws Exception {
        Integer[] list = new Integer[]{3, 3, 7, 8, 10};

        assertThat(ArrayTools.binarySearchLastOccur(list, 0, list.length, 3, Integer::compareTo)).isEqualTo(1);
        assertThat(ArrayTools.binarySearchLastOccur(list, 0, list.length, 11, Integer::compareTo)).isEqualTo(-6);
        assertThat(ArrayTools.binarySearchLastOccur(list, 0, 0, 3, Integer::compareTo)).isEqualTo(-1);
        assertThat(ArrayTools.binarySearchLastOccur(list, 2, 4, 8, Integer::compareTo)).isEqualTo(3);
    }
}