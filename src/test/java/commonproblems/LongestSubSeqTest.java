package commonproblems;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
/**
 * Created by 77060 on 2016/12/21.
 */
public class LongestSubSeqTest {

    @Test
    public void testLongestLength() throws Exception {
        int[] listA = new int[]{1,2,5,6,7,8,9};
        int[] listB = new int[]{1,6,2,5,8};
        int x = LongestSubSeq.longestLength(listA, listB);
        assertThat(x).isEqualTo(4);
        int[] listC = new int[0];
        int y = LongestSubSeq.longestLength(listA,listC);
        assertThat(y).isEqualTo(0);
    }
}