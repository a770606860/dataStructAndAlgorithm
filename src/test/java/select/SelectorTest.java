package select;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;


/**
 * Created by 77060 on 2016/12/19.
 */
public class SelectorTest {

    @Test
    public void testSelectXth() throws Exception {
        int[] list = new int[]{5,6,3,2,3,7,4};
        int x = Selector.selectXth(list, 2);
        assertThat(x).isEqualTo(3);
    }

    @Test
    public void testSelectIth2() throws Exception {
        int[] list = new int[] {4,6,7,3,1,2,5,8};
        int x = Selector.selectIth2(list, 0, list.length, 7);
        assertThat(x).isEqualTo(7);
    }
}