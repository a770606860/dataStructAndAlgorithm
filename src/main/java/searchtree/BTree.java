package searchtree;

import tools.ArrayTools;

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

    private int vSize;          // 在哪里消耗掉被插入元素，则在哪里进行自增操作
    private final int degree;
    private final int maxKeys;
    private Node root;
    private Node mChild;
    final Comparator<T> comparator;

    public BTree() {
        this(100, (a, b) -> ((Comparable<T>) a).compareTo(b));
    }

    public BTree(int degree, Comparator<T> comparator) {
        if(degree % 2 != 0) degree--;
        this.degree = degree;
        this.maxKeys = degree - 1;
        this.comparator = comparator;
        vSize = 0;
        root = null;
        mChild = null;
    }

    public List<V> search(T t) {
        if(mChild == null) return null;
        if(root == null) return getValues(mChild, mChild.findFirstOccurInLeaf(t));
        Node p = root;
        while(!p.isLeaf()) {
            p = p.getChildEqualOrBigger(p.findFirstOccurInInner(t));
        }
        int index = p.findFirstOccurInLeaf(t);
        return index < 0 ? new ArrayList<>() : getValues(p, index);
    }

    // 调用者确保mChild为叶节点，并且确保 index 有意义
    private List<V> getValues(Node leaf, int index) {
        List<V> list = new ArrayList<>();
        T y = leaf.getKey(index);
        list.add(leaf.getVal(index));
        index++;
        stop:
        while (leaf != null) {
            while(index != leaf.kSize) {
                T x = leaf.getKey(index);
                if(comparator.compare(x, y) == 0) list.add(leaf.getVal(index++));
                else break stop;
            }
            leaf = leaf.getNextNode();
            index = 0;
        }
        return list;
    }

    public void insert(T t, V v) {
        if(vSize == 0) {
            mChild = new Node(true);
            mChild.parent = root;
        }
        if(vSize < maxKeys) {
            insertIntoLeaf(mChild, t, v);
        }
        else if(vSize == maxKeys) {
            makeBtreeAndInsert(t, v);
        } else {
            // 如果根节点已满，则分裂根节点
            if(root.isFull()) {
                Node x = new Node(false);
                T key = (T) root.keys[maxKeys / 2];
                Node r = root.splitInner();
                x.insertInner(0, root, r);
                root = x;
            }

            Node p = root;
            while(!p.isLeaf()) {
                int index = p.findFirstOccurInInner(t);
                Node down = p.getChildEqualOrBigger(index);
                if(down.isFull()) {
                    if(down.isLeaf()) {
                        Node x = down.splitLeafAndInsert(t, v);
                        p.insertInner(index, down, x);
                        return;
                    }
                    Node x2 = down.splitInner();
                    p.insertInner(index, down, x2);
                    if(comparator.compare(t, x2.getKey(0)) < 0) p = down;
                    else p = x2;
                } else p = down;
            }
            insertIntoLeaf(p, t, v);
        }
    }

    private void makeBtreeAndInsert(T t, V v) {
        root = new Node(false);
        Node x = mChild.splitLeafAndInsert(t, v);
        root.insertInner(0, mChild, x);
    }

    // 插入前必须确保leaf节点为非满
    private void insertIntoLeaf(Node leaf, T t, V v) {
        if(leaf.isFull()) throw new RuntimeException("Insert error: Leaf is full");
        int index = leaf.findLastOccurInLeaf(t);
        index = index < 0 ? -(index + 1) : index + 1;
        leaf.insertLeaf(index, t, v);
    }

    public void delete(T t, V v) {

    }

    public int size() {
        return vSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // 这个类比较特殊存在以下几种情况：
    // 1，代表一个内部节点，且其孩子仍为内部节点，此时有 k(refers[i]) < keys[i] <= k(refers[i + 1])
    // 2, 代表一个内部节点，且其孩子为叶子节点
    // 3，代表一个叶子节点，此时有keys[i]所指向的具体引用为refers[i]，另外refers[size] 指向其右侧叶子节点
    // keys[kSize]的意义可以理解为存在一个无限大的哨兵关键字，keys[-1]可以理解为一个无限小的哨兵关键字
    private class Node {
        boolean isLeaf = false;             // 记录该节点是否为叶节点
        int kSize = 0;                   // 记录该节点大小(关键字个数)
        Node parent = null;                // 记录父亲节点，当然该算法的实现可能不需要记录父节点
        Object[] keys = new Object[degree - 1];
        Object[] refers = new Object[degree];

        Node(Boolean isLeaf) {
            this.isLeaf = isLeaf;
            kSize = 0;
            parent = null;
            keys = new Object[degree - 1];
            refers = new Object[degree];
        }

        T getKey(int index) {
            return (T) keys[index];
        }

        Node getChildSmaller(int index) {
            if(isLeaf) throw new UnsupportedOperationException("Needs innerNode");
            return (Node) refers[index];
        }

        Node getChildEqualOrBigger(int index) {
            if(isLeaf) throw new UnsupportedOperationException("Needs innerNode");
            return (Node) refers[index + 1];
        }

        // 调用者需确保节点非满
        void insertLeaf(int index, T t, V v) {
            if(!isLeaf) throw new UnsupportedOperationException("Needs leafNode");
            System.arraycopy(keys, index, keys, index + 1, kSize - index);
            System.arraycopy(refers, index, refers, index + 1, kSize - index);
            keys[index] = t;
            refers[index] = v;
            kSize++;
            vSize++;
        }

        // 调用者需确保节点非满
        void insertInner(int index, Node left, Node right) {
            if(isLeaf) throw new UnsupportedOperationException("Needs innerNode");
            System.arraycopy(keys, index, keys, index + 1, kSize - index);
            keys[index] = right.getKey(0);
            index++;
            System.arraycopy(refers, index, refers , index + 1, kSize + 1 - index);
            refers[index] = right;
            refers[index - 1] = left;
            kSize++;
            left.parent = this;
            right.parent = this;
        }

        V getVal(int index) {
            if(!isLeaf) throw new UnsupportedOperationException("Needs leafNode");
            return (V) refers[index];
        }

        boolean isLeaf() {
            return isLeaf;
        }

        boolean isFull() {
            return kSize == maxKeys;
        }

        Node splitLeafAndInsert(T t, V v) {
            if(!isLeaf) throw new UnsupportedOperationException("Needs leafNode");
            int index = ArrayTools.binarySearchLastOccur((T[]) this.keys, 0, kSize, t, comparator);
            if(index < 0) index = -(index + 1);
            else index++;
            Node x = new Node(true);
            int from = maxKeys / 2;
            Node tar = this;
            if(index > from) {
                from++;
                index -= from;
                tar = x;
            }
            // 拷贝
            System.arraycopy(this.keys, from, x.keys, 0, maxKeys - from);
            System.arraycopy(this.refers, from, x.refers, 0, maxKeys - from);
            // 设置源节点相关值
            Arrays.fill(this.keys, from, maxKeys, null);
            Arrays.fill(this.refers, from, maxKeys, null);
            this.kSize = from;
            // 设置新增节点相关值
            x.kSize = maxKeys - from;
            x.parent = this.parent;
            tar.insertLeaf(index, t, v);
            // 更新next指针
            x.refers[degree - 1] = this.refers[degree - 1];
            this.refers[degree - 1] = x;
            return x;
        }

        Node splitInner() {
            if(isLeaf) throw new UnsupportedOperationException("Needs innerNode");
            Node x = new Node(false);

            // 拷贝到新节点，并设置新节点相关值
            System.arraycopy(this.keys, maxKeys / 2 + 1, x.keys, 0, maxKeys / 2);
            System.arraycopy(this.refers, degree / 2, x.refers, 0, degree / 2);
            x.kSize = maxKeys / 2;
            x.parent = this.parent;
            for(int i = 0; i < degree / 2; i++) {
                ((Node)x.refers[i]).parent = x;
            }
            // 设置源节点相关值
            Arrays.fill(this.keys, maxKeys / 2, maxKeys, null);
            Arrays.fill(this.refers, degree / 2, degree, null);
            this.kSize = maxKeys / 2;

            return x;
        }

        public Node getNextNode() {
            if(!isLeaf) throw new UnsupportedOperationException("Needs leafNode");
            return (Node) refers[degree - 1];
        }

        public int findFirstOccurInLeaf(T t) {
            return ArrayTools.binarySearchFirstOccur((T[]) keys, 0, kSize, t, comparator);
        }

        public int findLastOccurInLeaf(T t) {
            return ArrayTools.binarySearchLastOccur((T[]) keys, 0, kSize, t, comparator);
        }

        public int findFirstOccurInInner(T t) {
            return ArrayTools.binarySearchFirstOccur((T[]) keys, 0, kSize, t, comparator);
        }

        public int findLastOccurInInner(T t) {
            return ArrayTools.binarySearchLastOccur((T[]) keys, 0, kSize, t, comparator);
        }
    }

    // 如果node具有相同的Key值，则返回最靠左的那个node
//    private Node searchInner(Node p, T t) {
//        int index = ArrayTools.binarySearchFirstOccur((T[]) p.keys, 0, p.kSize, t, comparator);
//        if(index < 0) return p.getChildSmaller(-(index + 1));
//        return p.getChildEqualOrBigger(index);
//    }

//    private List<V> searchLeaf(Node leaf, T t) {
//        List<V> list = new ArrayList<>();
//        int index = ArrayTools.binarySearchFirstOccur((T[]) leaf.keys, 0, leaf.kSize, t, comparator);
//        if(index < 0) return list;
//        else return getValues(leaf, index);
//
//    }

//    private Node splitLeafNodeAndInsert(Node leaf, T t, V v) {
//        int index = ArrayTools.binarySearchLastOccur((T[]) leaf.keys, 0, leaf.kSize, t, comparator);
//        if(index < 0) index = -(index + 1);
//        else index++;
//        Node x = new Node(true);
//        int from = maxKeys / 2;
//        Node tar = leaf;
//        if(index > from) {
//            from++;
//            index -= from;
//            tar = x;
//        }
//        // 拷贝
//        System.arraycopy(leaf.keys, from, x.keys, 0, maxKeys - from);
//        System.arraycopy(leaf.refers, from, x.refers, 0, maxKeys - from);
//        // 设置源节点相关值
//        Arrays.fill(leaf.keys, from, maxKeys, null);
//        Arrays.fill(leaf.refers, from, maxKeys, null);
//        leaf.kSize = leaf.kSize / 2 + 1;
//        // 设置新增节点相关值
//        x.kSize = leaf.kSize / 2 + 1;
//        x.parent = leaf.parent;
//        tar.insertLeaf(index, t, v);
//        // 更新next指针
//        x.refers[degree - 1] = leaf.refers[degree - 1];
//        leaf.refers[degree - 1] = x;
//        return x;
//    }
//
//    // 每次均按照中间节点划分，划分后原节点的中间关键字被删除，调用者需要提前保存该关键字
//    private Node splitInnerNode(Node node) {
//        Node x = new Node(false);
//
//        // 拷贝到新节点，并设置新节点相关值
//        System.arraycopy(node.keys, maxKeys / 2 + 1, x.keys, 0, maxKeys / 2);
//        System.arraycopy(node.refers, degree / 2, x.refers, 0, degree / 2);
//        x.kSize = maxKeys / 2;
//        x.parent = node.parent;
//        for(int i = 0; i < degree / 2; i++) {
//            ((Node)x.refers[i]).parent = x;
//        }
//        // 设置源节点相关值
//        Arrays.fill(node.keys, maxKeys / 2, maxKeys, null);
//        Arrays.fill(node.refers, degree / 2, degree, null);
//        node.kSize = maxKeys / 2;
//
//        return x;
//    }

    //    private int findInLeaf(Node leaf, T t) {
//        return ArrayTools.binarySearchFirstOccur((T[]) leaf.keys, 0, leaf.kSize, t, comparator);
//    }
//
//    private int findInInner(Node node, T t) {
//        int index = ArrayTools.binarySearchFirstOccur((T[]) node.keys, 0, node.kSize, t, comparator);
//        return index < 0 ? -(index + 1) - 1 : index;
//    }
}
