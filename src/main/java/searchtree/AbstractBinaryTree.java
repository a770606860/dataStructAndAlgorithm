package searchtree;

import java.util.*;

/**
 * Created by 77060 on 2017/2/18.
 */
// 实现方式为左边小右边大

/**
 * 继承自该类的具体子树的实现需要满足左子数在比较操作中小于右子树
 * @param <T>
 */
public abstract class AbstractBinaryTree<T> implements SearchTree<T> {

    protected abstract BiNode<T> getRootBiNode();

    protected Comparator<T> comparator;

    @Override
    public T search(T t) {
        BiNode<T> p = doSearch(t);
        return p == null ? null : p.getVal();
    }

    /**
     * 返回 null 如果未找到相应节点，否则返回相应节点
     * @param t
     * @return
     */
    protected BiNode<T> doSearch(T t) {
        BiNode<T> root = getRootBiNode();
        while(root != null) {
            int x = comparator.compare(t ,root.getVal());
            if(x < 0) root = root.getBiLeft();
            else if(x > 0) root = root.getBiRight();
            else break;
        }
        return root;
    }

    @Override
    public T minimum() {
        return isEmpty() ? null : minimum(getRootBiNode()).getVal();
    }

    @Override
    public T maximum() {
        return isEmpty() ? null : maximum(getRootBiNode()).getVal();
    }

    /**
     * 该函数不接受 null 为参数，寻找 biNode 子树的最小节点
     * @param biNode 非空，树中某个节点
     * @return 代表最小值的节点
     */
    protected BiNode<T> minimum(BiNode<T> biNode) {
        BiNode<T> p = biNode;
        while (p.hasBiLeft()) p = p.getBiLeft();
        return p;
    }

    /**
     * 该函数不接受 null 为参数，寻找 biNode 子树的最大节点
     * @param biNode 非空，树中某个节点
     * @return 代表最大值的节点
     */
    protected BiNode<T> maximum(BiNode<T> biNode) {
        BiNode<T> p = biNode;
        while (p.hasBiRight()) p = p.getBiRight();
        return p;
    }

    @Override
    public T predecessor(T t) {
        BiNode<T> nearest = getNearestNode(t);
        if(nearest == null) return null;
        if(comparator.compare(nearest.getVal(), t) < 0) return nearest.getVal();
        if(nearest.hasBiLeft()) return minimum(nearest.getBiLeft()).getVal();
        else {
            while(nearest.hasBiParent() && comparator.compare(nearest.getBiParent().getVal(), nearest.getVal()) > 0)
                nearest = nearest.getBiParent();
            return nearest.hasBiParent() ? nearest.getBiParent().getVal() : null;
        }
    }

    @Override
    public T successor(T t) {
        BiNode<T> nearest = getNearestNode(t);
        if(nearest == null) return null;

        if(comparator.compare(nearest.getVal(), t) > 0) return nearest.getVal();
        if(nearest.hasBiRight()) return minimum(nearest.getBiRight()).getVal();
        else {
            while(nearest.hasBiParent() && comparator.compare(nearest.getBiParent().getVal(), nearest.getVal()) < 0 )
                nearest = nearest.getBiParent();
            return nearest.hasBiParent() ? nearest.getBiParent().getVal() : null;
        }
    }

    protected BiNode<T> getNearestNode(T t) {
        BiNode<T> root = getRootBiNode();
        if(root == null) return null;
        while(true) {
            int x = comparator.compare(t, root.getVal());
            if(x < 0 && root.hasBiRight()) {
                root = root.getBiRight();
            } else if(x > 0 && root.hasBiLeft()) {
                root = root.getBiLeft();
            } else break;
        }

        return root;
    }


    public List<T> inOrderTravel() {
        List<T> list = new ArrayList<>();
        BiNode<T> p = getRootBiNode();
        Deque<BiNode<T>> deque = new ArrayDeque<>();

        // 不变式：p 是一颗子数的根或空，该子树是中序遍历的对象。栈中的子树的左子树正在被访问，右子树未被访问
        while(true) {
            if(p == null) {
                if(!deque.isEmpty()) {
                    p = deque.pop();
                    list.add(p.getVal());
                    p = p.getBiRight();
                } else break;
            } else {
                deque.push(p);
                p = p.getBiLeft();
            }
        }

        return list;
    }

    public List<T> preOrderTravel() {
        List<T> list = new ArrayList<>();
        BiNode<T> p = getRootBiNode();
        Deque<BiNode<T>> deque = new ArrayDeque<>();

        // 不变式：p 为某一颗子树的根，或为 null
        while(!(p == null && deque.isEmpty())) {
            if(p == null) {
                p = deque.pop();
            }
            list.add(p.getVal());
            if(p.hasBiRight()) deque.push(p.getBiRight());
            p = p.getBiLeft();
        }
        return list;
    }

    public List<T> postOrderTravel() {
        List<T> list = new ArrayList<>();
        BiNode<T> p = getRootBiNode();
        if(p == null) return list;
        Deque<BiNode<T>> lDeque = new ArrayDeque<>();
        Deque<BiNode<T>> rDeque = new ArrayDeque<>();

        // 不变式：p 子树从未被访问过(p 不为 null），lDeque 中节点的左子数正在被访问，rDeque 的右子树正在被访问
        while(true) {
            if(p.hasBiLeft()) {
                lDeque.push(p);
                p = p.getBiLeft();
            } else if(p.hasBiRight()) {
                rDeque.push(p);
                p = p.getBiRight();
            } else {
                while (true) {
                    list.add(p.getVal());
                    if(!rDeque.isEmpty() && p == rDeque.peek().getBiRight()) {
                        p = rDeque.pop();
                    } else if(!lDeque.isEmpty()) {
                        p = lDeque.pop();
                        if(p.hasBiRight()) {
                            rDeque.push(p);
                            p = p.getBiRight();
                            break;
                        }
                    } else return list;
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
