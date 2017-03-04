package dataStruct.binaryTree;

/**
 * Created by 77060 on 2017/1/5.
 */
public interface BinaryTree<T> {

    BinaryTree<T> getLeftChild();
    void setLeftChild(BinaryTree<T> leftChild);
    BinaryTree<T> getRightChild();
    void setRightChild(BinaryTree<T> rightChild);
    // 只有一个节点的树高度为0
    int getHigh();
}
