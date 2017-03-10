package others.disjointSet;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by 77060 on 2017/2/18.
 */
public class SimpleDisjointSetTest {

    @Test
    public void testForAll() throws Exception {
        SimpleDisjointSet<Integer> disjointSet = new SimpleDisjointSet<>();

        Integer x1 = 1;
        Integer x2 = 2;
        Integer x3 = 3;
        Integer x4 = 4;

        disjointSet.put(x1);
        SimpleDisjointSet.RecordNode<Integer> recordNode = disjointSet.getRecordNode(x1);

        disjointSet.put(x2);

        disjointSet.union(x1, x3);

        assertThat(disjointSet.isTogether(x1, x2)).isFalse();
        assertThat(disjointSet.isTogether(x2, x3)).isFalse();
        assertThat(disjointSet.isTogether(x1, x3)).isTrue();

        disjointSet.union(x1, x2);
        assertThat(disjointSet.isTogether(x1, x2));

        SimpleDisjointSet.RecordNode<Integer> recordNode1 = disjointSet.getRecordNode(x1);
        SimpleDisjointSet.RecordNode<Integer> recordNode2 = disjointSet.getRecordNode(x2);
        SimpleDisjointSet.RecordNode<Integer> recordNode3 = disjointSet.getRecordNode(x3);

        assertThat(recordNode == recordNode1 && recordNode == recordNode2 && recordNode2 == recordNode3).isTrue();

        assertThat(recordNode.getCount() == 3).isTrue();

        assertThat(disjointSet.setOf(x1).size()).isEqualTo(3);
    }
}