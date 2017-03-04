package dataStruct.binaryTree;

/**
 * Created by 77060 on 2017/1/5.
 */
public class LinkedBinaryTree<T> implements BinaryTree<T> {

    private LinkedBinaryTree<T> leftChild;
    private LinkedBinaryTree<T> rightChild;
    private T val;

    public LinkedBinaryTree(T val, LinkedBinaryTree<T> leftChild, LinkedBinaryTree<T> rightChild) {
        this.val = val;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public LinkedBinaryTree(T val) {
        this(val, null, null);
    }

    public LinkedBinaryTree() {
        this(null, null, null);
    }

    @Override
    public BinaryTree<T> getLeftChild() {
        return leftChild;
    }

    @Override
    public void setLeftChild(BinaryTree<T> leftChild) {
        if(leftChild instanceof LinkedBinaryTree)
            this.leftChild = (LinkedBinaryTree<T>) leftChild;
        else
            throw new IllegalArgumentException("Not type of LinkedBinaryTree");
    }


    @Override
    public BinaryTree<T> getRightChild() {
        return rightChild;
    }

    @Override
    public void setRightChild(BinaryTree<T> rightChild) {
        if(rightChild instanceof LinkedBinaryTree)
            this.rightChild = (LinkedBinaryTree<T>) rightChild;
        else
            throw new IllegalArgumentException("Not type of LinkedBinaryTree");
    }


    @Override
    public int getHigh() {
        int leftH = 0;
        int rightH = 0;
        if(leftChild != null) leftH = leftChild.getHigh();
        if(rightChild != null) rightH = rightChild.getHigh();
        return Math.max(leftH, rightH);
    }

}
