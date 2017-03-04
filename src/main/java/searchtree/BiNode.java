package searchtree;

/**
 * 任何具体二叉树的节点均实现该接口
 * Created by 77060 on 2017/2/23.
 */
public interface BiNode<T> {
    BiNode<T> getBiLeft();
    BiNode<T> getBiRight();
    BiNode<T> getBiParent();

    boolean hasBiLeft();
    boolean hasBiRight();
    boolean hasBiParent();

    T getVal();
}
