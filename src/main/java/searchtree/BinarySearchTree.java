package searchtree;

import java.util.Comparator;

/**
 * Created by 77060 on 2017/2/22.
 */
public class BinarySearchTree<T> extends AbstractBinaryTree<T> {

    private BSTNode head;
    private int size;

    final static int LEFT = 0;
    final static int RIGHT = 1;

    @SuppressWarnings("unchecked")
    public BinarySearchTree() {
        this((o1, o2) -> ((Comparable<T>) o1).compareTo(o2));
    }

    public BinarySearchTree(Comparator<T> comparator) {
        size = 0;
        this.comparator = comparator;
        head = new BSTNode(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected BiNode<T> getRootBiNode() {
        return getRoot();
    }

    @Override
    public void insert(T t) {
        BSTNode n = new BSTNode(t);
        if (getRoot() == null) {
            setRoot(n);
            return;
        }

        BSTNode p = getRoot();
        while (true) {
            int x = comparator.compare(t, p.val);
            if (x == 0) return;
            else if (x < 0 && p.left != null) p = p.left;
            else if (x > 0 && p.right != null) p = p.right;
            else {
                if (x < 0) connect(p, n, LEFT);
                else connect(p, n, RIGHT);
                size++;
                return;
            }
        }
    }

    // 使用此函数确保p真实存在，否则抛出 NullPointerException 异常
    private void connect(BSTNode p, BSTNode child, int d) {
        if (d == LEFT) p.left = child;
        else p.right = child;
        if (child != null) {
            child.parent = p;
        }
    }

    @Override
    public void delete(T t) {
        BSTNode node = (BSTNode) doSearch(t);
        if (node == null) return;

        size--;
        int DIR = node.parent.left == node ? LEFT : RIGHT;
        BSTNode p = node.parent;
        if (!node.hasBiRight()) {
            connect(p, node.left, DIR);
        } else if (!node.hasBiLeft()) {
            connect(p, node.right, DIR);
        } else {
            BSTNode min = (BSTNode) minimum(node.right);
            if (min == node.right) {
                connect(p, node.right, DIR);
                connect(node.right, node.left, LEFT);
            } else {
                BSTNode r = node.right, q = min.parent, h = min.right, l = node.left;
                connect(p, min, DIR);
                connect(min, l, LEFT);
                connect(min, r, RIGHT);
                connect(q, h, LEFT);
            }
        }

    }


    @Override
    public int size() {
        return size;
    }

    private BSTNode getRoot() {
        return head.left;
    }

    private void setRoot(BSTNode root) {
        head.left = root;
        root.parent = head;
        size++;
    }

    private class BSTNode implements BiNode {
        T val;
        BSTNode left, right, parent;

        BSTNode(T val) {
            this(val, null, null, null);
        }

        BSTNode(T val, BSTNode left, BSTNode right, BSTNode parent) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public BiNode getBiLeft() {
            return left;
        }

        @Override
        public BiNode getBiRight() {
            return right;
        }

        @Override
        public BiNode getBiParent() {
            return parent;
        }

        @Override
        public boolean hasBiLeft() {
            return left != null;
        }

        @Override
        public boolean hasBiRight() {
            return right != null;
        }

        @Override
        public boolean hasBiParent() {
            return parent != null;
        }

        @Override
        public T getVal() {
            return val;
        }
    }

}
