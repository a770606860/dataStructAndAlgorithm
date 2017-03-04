package dataStruct.binaryTree;

/**
 * Created by 77060 on 2017/1/5.
 */
public class BinatryTrees {
    public static <T> int getDiameter(BinaryTree<T> tree) {
        return doGetDiameter(tree)[1];
    }

    public static <T> int[] doGetDiameter(BinaryTree<T> tree) {
        if (tree == null) return new int[]{-1, 0};
        else {
            int[] L = doGetDiameter(tree.getLeftChild());
            int[] R = doGetDiameter(tree.getRightChild());

            return new int[]{Math.max(L[0], R[0]) + 1, Math.max(L[0] + R[0] + 2, Math.max(L[1], R[1]))};
        }
    }
}
