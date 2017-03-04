package BinarySerchTree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static com.google.common.truth.Truth.assertThat;

/**
 * Created by 77060 on 2016/12/15.
 */
public class BSTreeTest {

    @Test
    public void test() {
        BSTree<Integer> bsTree = new BSTree<Integer>();

        bsTree.insert(5);
        bsTree.insert(8);
        bsTree.insert(2);
        bsTree.insert(7);
        bsTree.insert(1);

        List<Integer> list = bsTree.inOrderTravel();
        List<Integer> list2 = Arrays.asList(1, 2, 5, 7, 8);
        List<Integer> list3 = Arrays.asList(1, 2, 7, 8 ,5);
        assertThat(list).isEqualTo(list2);
        assertThat(bsTree.postOrderTravel()).isEqualTo(list3);
    }

}