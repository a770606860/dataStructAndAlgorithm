package searchtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 这是一个在内存中的B+树，并且由于 java 只存在堆变量的原因，实用性不强，作为编码练习
 * 结构性规则：假设最大度为n（偶数），除了父节点外，每个节点最多包含n - 1个关键字，最少包含 n / 2 - 1 个关键字
 *             内部节点：对于关键字ak，其做区间的值均小于ak，右区间的值大于等于 ak，右区间的值均大于 ak
 *             假设关键字的下表为 ki, 那么有 nodei 中的所有关键字均小于 ki, node(i + 1)均 >= ki
 * 当总的关键字个数不多于 n - 1 个时，不存在父节点(root == null)，只有一个叶节点
 * Created by 77060 on 2017/3/3.
 */
public class BTree<T, V> {

    private int tSize;
    private int degree;
    private Node root;
    private Node mChild;
    Comparator<T> comparator;

    public BTree() {
        this(100, (a, b) -> ((Comparable<T>) a).compareTo(b));
    }

    public BTree(int degree, Comparator<T> comparator) {
        if(degree % 2 != 0) degree--;
        this.degree = degree;
        this.comparator = comparator;
        tSize = 0;
        root = null;
        mChild = null;
    }

    public List<V> search(T t) {
        if(mChild == null) return null;
        if(root == null) return searchLeaf(mChild, t);
        Node p = root;
        while(!p.isLeaf()) {
            p = searchInner(p, t);
        }
        return searchLeaf(p, t);
    }

    // 如果node具有相同的Key值，则返回最靠左的那个node
    private Node searchInner(Node p, T t) {
        int index = findBiggerOrEqual(p, t);
        if(index == p.kSize) return p.getNodeSmaller(index);
        return p.getNodeEqualOrBigger(index);
    }

    private List<V> searchLeaf(Node mChild, T t) {
        List<V> list = new ArrayList<>();
        int index = findBiggerOrEqual(mChild, t);
        if(index == mChild.kSize) return list;
        else return getValues(mChild, index);
    }

    // 调用者确保mChild为叶节点
    private List<V> getValues(Node mChild, int index) {
        List<V> list = new ArrayList<>();
        T y = mChild.getKey(index);
        list.add(mChild.getValInKey(index));
        index++;
        stop:
        while (mChild != null) {
            while(index != mChild.kSize) {
                T x = mChild.getKey(index);
                if(comparator.compare(x, y) == 0) list.add(mChild.getValInKey(index++));
                else break stop;
            }
            mChild = mChild.getNextNode();
            index = 0;
        }
        return list;
    }

    // 查找范围锁定在node中
    private int findBiggerOrEqual(Node node, T t) {
        int lt = 0, r = node.kSize;
        // 退出循环时的终止状态
        //  1，当 lt == kSize，表示 lt 大于任何关键字
        //  2, 否则有[0, lt) < t, [lt, 0-0) >= t
        while (lt < r) {
            int mid = (lt + r) / 2;
            int x = comparator.compare(t, node.getKey(mid));
            if(x >= 0) r = mid;
            else lt = mid + 1;
        }
        return lt;
    }

    // 查找范围锁定在node中
    private int findSmallOrEqual(Node node, T t) {
        int lt = 0, r = node.kSize;
        while (lt < r) {
            int mid = (lt + r) / 2;
            int x = comparator.compare(t, node.getKey(mid));
            if(x <= 0) lt = mid + 1;
            else r = mid;
        }
        return lt - 1;
    }

    public void insert(T t, V v) {
        if(tSize < maxNodeKeySize())
            ordinaryInsert(t, v);
    }

    private void ordinaryInsert(T t, V v) {
        int index = findSmallOrEqual(mChild, t);
        // 此时index一定存在意义
        Arrays.binarySearch();
    }

    public void delete(T t, V v) {

    }

    public int maxNodeKeySize() {
        return degree - 1;
    }

    public int size() {
        return tSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // 这个类比较特殊存在以下几种情况：
    // 1，代表一个内部节点，且其孩子仍为内部节点，此时有 k(refers[i]) < k(keys[i]) <= k(refers[i + 1])
    // 2, 代表一个内部节点，且其孩子为叶子节点
    // 3，代表一个叶子节点，此时有keys[i]所指向的具体引用为refers[i]，另外refers[size] 指向其右侧叶子节点
    // keys[kSize]的意义可以理解为存在一个无限大的哨兵关键字，keys[-1]可以理解为一个无限小的哨兵关键字
    private class Node {
        boolean isLeaf;             // 记录该节点是否为叶节点
        int kSize;                   // 记录该节点大小(关键字个数)
        Node parent;                // 记录父亲节点
        Object[] keys = new Object[degree - 1];
        Object[] refers = new Object[degree];

        // 非法调用抛出越界异常
        T getKey(int index) {
            return (T) keys[index];
        }

        // 非法调用抛出异常
        Node getNodeSmaller(int index) {
            return (Node) refers[index];
        }

        Node getNodeEqualOrBigger(int index) { return (Node) refers[index + 1];}

        // 非法调用抛出异常
        V getValInKey(int index) {
            return (V) refers[index];
        }

        boolean isLeaf() {return isLeaf;}

        public Node getNextNode() {
            return (Node) refers[degree - 1];
        }
    }
}
