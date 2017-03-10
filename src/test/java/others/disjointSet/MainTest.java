package others.disjointSet;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import static com.google.common.truth.Truth.assertThat;
/**
 * Created by 77060 on 2017/2/18.
 */
public class MainTest {

    @Test
    public void testMain() throws Exception {
        Set<Integer> set = new HashSet<>();
        Integer a1 = 1;
        Integer a2 = 1;
        set.add(a1);
        set.remove(a2);
        assertThat(set.isEmpty()).isTrue();

    }
}