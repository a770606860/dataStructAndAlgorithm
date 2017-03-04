package commonproblems;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
/**
 * Created by 77060 on 2016/12/21.
 */
public class OptimalBinaryTreeTest {

    @Test
    public void testFindOptimalCost() throws Exception {
        int[] listA = new int[]{15, 10, 5, 10, 20};    //
        int[] listB = new int[]{5, 10, 5, 5, 5, 10}; //
        int x = OptimalBinaryTree.findOptimalCost(listA, listB);
        assertThat(x).isEqualTo(275);
    }
}