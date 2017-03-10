package others.commonproblems;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by 77060 on 2016/12/17.
 */
public class DeleteAllInListTest {

    @Test
    public void testDeleteAllFromListAndResize() throws Exception {
        int[] list = new int[]{1, 2,3,6,8,7,7,4,5};
        int[] list2 = new int[]{1,2,3,6,8, 4,5};
        assertThat(DeleteAllInList.deleteAllFromListAndResize(list, 7)).isEqualTo(list2);


    }

    @Test
    public void testDeleteAllFromListAndRearrange() throws Exception {
        int[] list = new int[]{1, 2,3,6,8,7,7,4,5};
        int[] list2 = new int[]{1,2,3,6,8,5,4};
        assertThat(DeleteAllInList.deleteAllFromListAndRearrange(list, 7)).isEqualTo(list2);
    }
}