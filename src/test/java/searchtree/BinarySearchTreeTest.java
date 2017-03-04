package searchtree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
/**
 * Created by 77060 on 2017/3/2.
 */
public class BinarySearchTreeTest {

    @Test
    public void testInsert() throws Exception {
        SearchTree<Integer> tree = new BinarySearchTree<>();

        tree.insert(3);
        tree.insert(8);
        tree.insert(2);
        tree.insert(4);
        tree.insert(4);
        tree.insert(10);
        tree.delete(4);
        tree.delete(3);
        tree.delete(4);
        tree.delete(9);
        tree.insert(9);
        tree.insert(9);
        tree.insert(100);
        tree.insert(50);
        tree.insert(25);
        tree.insert(75);
        tree.insert(150);
        tree.insert(90);
        tree.insert(95);
        tree.insert(80);
        tree.insert(40);

        tree.delete(100);


        List<Integer> list = ((BinarySearchTree<Integer>) tree).inOrderTravel();

        assertThat(tree.size()).isEqualTo(12);
        assertThat(list).isEqualTo(Arrays.asList(2,8,9,10,25,40,50,75,80,90,95,150));
        list = ((BinarySearchTree) tree).preOrderTravel();
        assertThat(list).isEqualTo(Arrays.asList(8, 2, 10, 9, 150, 50, 25, 40, 75, 90, 80, 95));
        list = ((BinarySearchTree) tree).postOrderTravel();
        assertThat(list).isEqualTo(Arrays.asList(2, 9, 40, 25, 80, 95, 90, 75, 50, 150, 10, 8));
        ((BinarySearchTree) tree).insert(77);
        ((BinarySearchTree) tree).insert(78);
        ((BinarySearchTree) tree).insert(79);
        list = ((BinarySearchTree) tree).inOrderTravel();
        assertThat(list).isEqualTo(Arrays.asList(2,8,9,10,25,40,50,75,77, 78, 79,80,90,95,150));
        ((BinarySearchTree) tree).delete(90);
        list = ((BinarySearchTree) tree).inOrderTravel();
        assertThat(list).isEqualTo(Arrays.asList(2,8,9,10,25,40,50,75,77, 78, 79,80,95,150));
        assertThat(tree.size()).isEqualTo(list.size());

//        tree.delete(8);
//        tree.delete(2);
//        tree.delete(10);
//        tree.delete(2);
//        tree.delete(3);
//        assertThat(tree.isEmpty()).isTrue();
    }
}