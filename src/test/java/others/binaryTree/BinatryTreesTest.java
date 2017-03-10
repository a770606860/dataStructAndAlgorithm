package others.binaryTree;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

/**
 * Created by 77060 on 2017/1/5.
 */
public class BinatryTreesTest {

    @Test
    public void testGetDiameter() throws Exception {
        BinaryTree<Integer> a = new LinkedBinaryTree<>();
        BinaryTree<Integer> b = new LinkedBinaryTree<>();
        BinaryTree<Integer> c = new LinkedBinaryTree<>();
        BinaryTree<Integer> d = new LinkedBinaryTree<>();
        BinaryTree<Integer> e = new LinkedBinaryTree<>();
        BinaryTree<Integer> f = new LinkedBinaryTree<>();
        BinaryTree<Integer> g = new LinkedBinaryTree<>();
        BinaryTree<Integer> h = new LinkedBinaryTree<>();
        BinaryTree<Integer> i = new LinkedBinaryTree<>();
        a.setLeftChild(b);
        a.setRightChild(c);
        b.setLeftChild(d);
        b.setRightChild(e);
        d.setLeftChild(f);
        e.setRightChild(g);
        f.setLeftChild(h);
        g.setRightChild(i);

        assertThat(BinatryTrees.getDiameter(a)).isEqualTo(6);
        assertThat(BinatryTrees.getDiameter(e)).isEqualTo(2);

    }
}