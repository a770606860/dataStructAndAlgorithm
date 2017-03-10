package others.disjointSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 77060 on 2017/2/17.
 * 使用：
 *      实例化一个简单不相交集（SimpleDisjointSet）对象，利用该对象管理集合的创建，合并，以及判断两元素是否在同一集合内等操作
 *
 */
public class SimpleDisjointSet<T> implements DisjointSet<T> {

    // 不变式：新建的 RecordNode 没有记录 Node，count 属性为0
    public static class RecordNode<T> {
        private Node<T> head;
        private Node<T> tail;
        private int count;

        public RecordNode() {
            head = null;
            tail = null;
            count = 0;
        }

        public Node<T> getHead() {
            return head;
        }

        public void setHead(Node<T> head) {
            this.head = head;
        }

        public Node<T> getTail() {
            return tail;
        }

        public void setTail(Node<T> tail) {
            this.tail = tail;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    // 不变式，新建的Node next 为空，不属于任何一个集合
    private static class Node<T> {
        private Node<T> next;
        private RecordNode<T> recordNode;
        private T val;

        public Node(T t) {
            val = t;
            next = null;
            recordNode = null;
        }


        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public RecordNode<T> getRecordNode() {
            return recordNode;
        }

        public void setRecordNode(RecordNode<T> recordNode) {
            this.recordNode = recordNode;
        }

        public T getVal() {
            return val;
        }

        public void setVal(T val) {
            this.val = val;
        }
    }

    private HashMap<T, Node<T>> map = new HashMap<>();

    public SimpleDisjointSet() {
    }

    @Override
    public void put(T t) {
        doPut(t);
    }

    // 新建 Node 以及 RecordNode，设置相应属性
    private Node<T> doPut(T t) {
        Node<T> node = new Node<>(t);
        RecordNode<T> recordNode = new RecordNode<>();
        node.setRecordNode(recordNode);
        recordNode.setCount(1);
        recordNode.setHead(node);
        recordNode.setTail(node);
        map.put(t, node);
        return node;
    }

    /**
     *  合并 t2 与 t1 所在集合，若 t1 或 t2 均不属于任意集合，则新建集合
     */
    @Override
    public void union(T t1, T t2) {
        Node<T> node1 = map.get(t1);
        Node<T> node2 = map.get(t2);
        if(node1 == null) node1 = doPut(t1);
        if(node2 == null) node2 = doPut(t2);
        if(node1.getRecordNode().getCount() < node2.getRecordNode().getCount())
            doUnion(node2, node1);
        else
            doUnion(node1, node2);

    }

    // 将 t2 所在集合并入到 t1 所在集合中
    // 前提：t1 与 t2 为某一集合中的元素
    private void doUnion(Node<T> t1, Node<T> t2) {
        RecordNode<T> recordNode = t1.getRecordNode();
        recordNode.setCount(recordNode.getCount() + t2.getRecordNode().getCount());
        Node<T> p = t2.getRecordNode().getHead();
        recordNode.getTail().setNext(p);
        recordNode.setTail(t2.getRecordNode().getTail());
        while(p != null) {
            p.setRecordNode(recordNode);
            p = p.getNext();
        }
    }

    @Override
    public boolean isTogether(T t1, T t2) {
        Node<T> n1 = map.get(t1), n2 = map.get(t2);
        return !(n1 == null || n2 == null) && n1.getRecordNode() == n2.getRecordNode();
    }

    @Override
    public T findSet(T t) {
        Node<T> node = map.get(t);
        if(node == null) node = doPut(t);
        return node.getRecordNode().getHead().getVal();
    }

    @Override
    public List<T> setOf(T t) {
        Node<T> n = map.get(t);
        List<T> list = new ArrayList<>();
        if(n == null)
            n = doPut(t);
        n = n.getRecordNode().getHead();
        while(n != null) {
            list.add(n.getVal());
            n = n.getNext();
        }
        return list;
    }

    public RecordNode<T> getRecordNode(T t) {
        Node<T> n = map.get(t);
        if(n == null) n = doPut(t);
        return n.getRecordNode();
    }
}
