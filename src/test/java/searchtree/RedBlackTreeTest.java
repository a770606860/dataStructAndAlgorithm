package searchtree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by 77060 on 2017/3/2.
 */
public class RedBlackTreeTest {

    public RedBlackTree build() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(41);
        tree.insert(38);
        tree.insert(31);    // case 3
        tree.insert(12);
        tree.insert(19);    // case 2
        tree.insert(8);
        return tree;
    }

    @Test
    public void testInsert() throws Exception {
        RedBlackTree<Integer> tree = build();
        List<Integer> list = tree.preOrderTravel();
        assertThat(list).isEqualTo(Arrays.asList(38, 19, 12, 8,31,41));

        assertThat(tree.preOrderTravelNode().stream().map(e -> e.color).collect(Collectors.toList())).
                isEqualTo(Arrays.asList(RedBlackTree.Color.BLACK, RedBlackTree.Color.RED, RedBlackTree.Color.BLACK,
                RedBlackTree.Color.RED, RedBlackTree.Color.BLACK, RedBlackTree.Color.BLACK));

        tree.insert(9);
        tree.insert(7);     // case 1 -> case 3

        assertThat(tree.inOrderTravel()).isEqualTo(Arrays.asList(7,8,9,12,19,31,38,41));
        assertThat(tree.preOrderTravelNode().stream().map(e -> e.color).collect(Collectors.toList())).
                isEqualTo(Arrays.asList(RedBlackTree.Color.BLACK, RedBlackTree.Color.RED, RedBlackTree.Color.BLACK,
                RedBlackTree.Color.RED, RedBlackTree.Color.BLACK, RedBlackTree.Color.RED, RedBlackTree.Color.BLACK, RedBlackTree.Color.BLACK));
        assertThat(tree.maximum()).isEqualTo(41);
        assertThat(tree.minimum()).isEqualTo(7);
        assertThat(tree.search(8)).isEqualTo(8);
        assertThat(tree.predecessor(9)).isEqualTo(8);
        assertThat(tree.successor(13)).isEqualTo(19);

        // 插入完成后的树结构如下图(带有'表示其颜色为黑，反之为红)
        //                  19'
        //            /             \
        //           12             38
        //        /       \       /    \
        //      8'          9'   31'    41'
        //     /
        //    7

    }

    @Test
    public void testDelete() throws Exception {
        RedBlackTree<Integer> tree = build();

        tree.delete(8);
        tree.delete(12);
        tree.delete(19);
        tree.delete(31);
        assertThat(tree.inOrderTravel()).isEqualTo(Arrays.asList(38, 41));
        assertThat(tree.preOrderTravelNode().stream().map(e -> e.color).collect(Collectors.toList())).
                isEqualTo(Arrays.asList(RedBlackTree.Color.BLACK, RedBlackTree.Color.RED));
    }
}