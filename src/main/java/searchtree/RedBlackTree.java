package searchtree;

import java.util.*;

/**
 * 结构性规则：1, 当树为空时，NIL 成为 root，对于整个树的结构，NIL 的父节点没有确切意义
 *             2, root 的父节点为 NIL，所有叶节点为 NIL
 * Created by 77060 on 2017/2/23.
 */
public class RedBlackTree<T> extends AbstractBinaryTree<T> {

    RBNode root;
    // NIL的父亲只在删除操作的时候会有意义，其他情况下均无意义
    final RBNode NIL = new RBNode(null, Color.BLACK);
    int size;
    final static int LEFT = 330;
    final static int RIGHT = 331;
    final static int PARENT = 332;

    @SuppressWarnings("unchecked")
    public RedBlackTree() {
        this((o1, o2) -> ((Comparable<T>)o1).compareTo(o2));
    }

    public RedBlackTree(Comparator<T> comparator) {
        this.comparator = comparator;
        size = 0;
        root = NIL;
        root.parent = NIL;
    }

    @Override
    protected BiNode<T> getRootBiNode() {
        return root == NIL ? null : root;
    }

    @Override
    public void insert(T t) {
        if(root == NIL) {
            root = new RBNode(t, Color.BLACK, NIL, NIL, NIL);
            size++;
        } else {
            RBNode p = root;
            while (true) {
                int x = comparator.compare(t, p.val);
                if (x == 0) return;
                else if (x < 0 && p.hasBiLeft()) p = p.left;
                else if (x > 0 && p.hasBiRight()) p = p.right;
                else {
                    RBNode n = new RBNode(t, Color.RED, NIL, NIL, p);
                    if (x < 0)
                        p.left = n;
                    else p.right = n;
                    insertFixUp(n);
                    size++;
                    break;
                }
            }
        }
    }

    private void insertFixUp(RBNode z) {
        while(z.parent.color == Color.RED) {
            RBNode y = z.parent.parent.left;
            int yDIR = LEFT;
            if(z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                yDIR = RIGHT;
            }
            if(y.color == Color.RED) {
                z = z.parent.parent;
                z.color = Color.RED;
                z.left.color = Color.BLACK;
                z.right.color = Color.BLACK;
            }
            else {
                int zDIR = dir(z.parent, z);
                if(yDIR == zDIR) {
                    z = z.parent;
                    rotate(z, getReverse(zDIR));
                }
                z.parent.color = Color.BLACK;
                z.parent.parent.color = Color.RED;
                rotate(z.parent.parent, yDIR);
            }
        }
        root.color = Color.BLACK;
    }

    // 该操作负责修复根节点，左旋就是右提，右旋就是左提
    private void rotate(RBNode x, int DIR) {
        RBNode y = x.right;
        RBNode b = y.left;
        if(DIR == LEFT) {
            connect(x.parent, y, dir(x.parent, x));
            connect(y, x, LEFT);
            connect(x, b, RIGHT);
        } else {
            y = x.left;
            b = y.right;
            connect(x.parent, y, dir(x.parent, x));
            connect(y, x, RIGHT);
            connect(x, b, LEFT);
        }
    }

    // 负责修复root属性
    private void connect(RBNode p, RBNode c, int dir) {
        if(p == NIL) {
            root = c;
        } else if(dir == LEFT) {
            p.left = c;
        } else p.right = c;
        c.parent = p;
    }

    private int dir(RBNode p, RBNode c) {
        if(p.left == c) return LEFT;
        return RIGHT;
    }

    private int getReverse(int DIR) {
        if(DIR == LEFT) return RIGHT;
        return LEFT;
    }



    @Override
    public void delete(T t) {
        RBNode z = (RBNode) doSearch(t);
        if(z == null) return;

        Color yOriCol = z.color;
        RBNode x, y = z;
        // 在每一种情况下检查 NIL 是否有父节点
        size--;
        if(z.left == NIL) {
            x = z.right;
            connect(z.parent, x, dir(z.parent, z));     // NIL
        } else if(z.right == NIL) {
            x = z.left;
            connect(z.parent, x, dir(z.parent, z));     // NIL
        } else {
            y = (RBNode) minimum(z.right);
            yOriCol = y.color;
            x = y.right;
            connect(z.parent, y, dir(z.parent, z));
            connect(y, z.left, LEFT);
            connect(y, x, RIGHT);   // NIL
            if(y != z.right) {
                RBNode h = y.parent;
                connect(y, z.right, RIGHT);
                connect(h, x, LEFT);    // NIL
            }
            y.color = z.color;
        }
        if(yOriCol == Color.BLACK)
            deleteFixUp(x);
    }

    // 前提条件：如果 x 为 NIL，那么此时 NIL 的父节点有确切意义
    private void deleteFixUp(RBNode x) {
        while(x != root && x.color == Color.BLACK) {
            int xDir = dir(x.parent, x);
            RBNode w = x.parent.left;
            if(xDir == LEFT) w = x.parent.right;
            if(w.color == Color.RED) {
                x.parent.color = Color.RED;
                w.color = Color.BLACK;
                w = w.getChild(xDir);
                rotate(x.parent, xDir);
            }
            if(w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                w.color = Color.RED;
                x = x.parent;
            } else {
                if(w.getChild(getReverse(xDir)).color == Color.BLACK) {
                    w.color = Color.RED;
                    w.getChild(xDir).color = Color.BLACK;
                    rotate(w, getReverse(xDir));
                    w = w.parent;
                }
                w.color = w.parent.color;
                w.parent.color = Color.BLACK;
                w.getChild(getReverse(xDir)).color = Color.BLACK;
                rotate(w.parent, xDir);
                x = root;   // 令程序退出
            }
        }
        x.color = Color.BLACK;
    }

    @Override
    public int size() {
        return size;
    }

    public enum Color{
        RED,BLACK
    }

    public List<RBNode> preOrderTravelNode() {
        List<RBNode> list = new ArrayList<>();
        Deque<BiNode> deque = new ArrayDeque<>();
        BiNode p = getRootBiNode();

        while(!(p == null && deque.isEmpty())) {
            if(p == null) p = deque.pop();
            list.add((RBNode) p);
            if(p.hasBiRight()) deque.push(p.getBiRight());
            p = p.getBiLeft();
        }
        return list;
    }

    public class RBNode implements BiNode {
        T val;
        RBNode left, right, parent;
        Color color;

        RBNode(T val, Color color) {
            this(val, color, null, null, null);
        }

        RBNode(T val, Color color, RBNode left, RBNode right, RBNode parent) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.color = color;
        }

        @Override
        public BiNode getBiLeft() {
            return left == NIL ? null : left;
        }

        @Override
        public BiNode getBiRight() {
            return right == NIL ? null : right;
        }

        @Override
        public BiNode getBiParent() {
            return parent == NIL ? null : parent;
        }

        @Override
        public boolean hasBiLeft() {
            return left != null && left != NIL;
        }

        @Override
        public boolean hasBiRight() {
            return right != null && right != NIL;
        }

        @Override
        public boolean hasBiParent() {
            return parent != null && parent != NIL;
        }

        @Override
        public T getVal() {
            return val;
        }

        public RBNode getChild(int dir) {
            if(dir == LEFT) return left;
            else return right;
        }

    }
}
