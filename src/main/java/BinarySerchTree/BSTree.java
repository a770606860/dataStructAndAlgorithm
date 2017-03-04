package BinarySerchTree;

import java.util.*;

/**
 * Created by 77060 on 2016/12/14.
 */

public  class BSTree<T>{

private static class Node<T> {
    public T val;
    public Node<T> left;
    public Node<T> right;

    public Node() {
        left = null;
        right = null;
    }

    public Node(T val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

    private Node<T> root = null;
    private static final Node NIL = null;
    private Comparator<T> comparator;

    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int NONED = 3;

    @SuppressWarnings("unchecked")
    public BSTree() {
        this.comparator = (o1, o2) -> ((Comparable<T>) o1).compareTo(o2);
    }

    public BSTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public List<T> preOrderTravel() {
        List<T> result = new ArrayList<>();
        if(isEmpty()) return result;
        Deque<Node<T>> deque = new ArrayDeque<>();
        Node<T> p = root;

        while(true) {
            while(p != null) {
                result.add(p.val);
                if(p.right != null) deque.push(p.right);
                p = p.left;
            }
            if(deque.isEmpty()) break;
            p = deque.pop();
        }
        return result;
    }


    // 测试正确
    public List<T> inOrderTravel() {
        List<T> result = new ArrayList<>();
        Deque<Node<T>> stack = new ArrayDeque<>();
        Node<T> p = root;
        if (isEmpty()) return result;

        // 不变式是什么？
        while (true) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            if (stack.isEmpty()) break;
            p = stack.pop();
            result.add(p.val);
            p = p.right;
        }
        return result;
    }

    // 测试正确
    public List<T> postOrderTravel() {
        List<T> result = new ArrayList<>();
        if(isEmpty()) return result;
        Deque<Node<T>> dequeR = new ArrayDeque<>(); // 未访问过右孩子的节点
        Deque<Node<T>> deque = new ArrayDeque<>();  // 访问过右孩子的节点，待访问自身
        Node<T> p = root;

        // 不变式：进入循环前保证p是未访问过右孩子的节点
        while(true) {
            while(p != null) {
                dequeR.push(p);
                p = p.left;
            }
            p = dequeR.pop();
            deque.push(p);
            p = p.right;

            // 维持不变式性质
            while(p == null && !deque.isEmpty()) {
                p = deque.pop();
                result.add(p.val);
                if(dequeR.isEmpty() || p != dequeR.peek().left) p = null;
                else {
                    p = dequeR.pop();
                    deque.push(p);
                    p = p.right;
                }
            }
            if(p == null) break;
        }
        return result;
    }

    public boolean isEmpty() {
        return root == null;
    }

    // 测试正确
    public void insert(T val) {
        Node<T> valNode = new Node<>(val);
        if(root == null) {
            root = valNode;
            return;
        }
        Node<T> p = root;
        Node<T> pre = null;
        while(p != null) {
            pre = p;
            if(comparator.compare(val, p.val) <= 0) p = p.left;
            else p = p.right;
        }
        if(comparator.compare(val, pre.val) <= 0) pre.left = valNode;
        else pre.right = valNode;
    }

    public T search(T val) {
        Node<T> p = root;
        while(p != null && comparator.compare(p.val, val) != 0) {
            if(comparator.compare(p.val, val) > 0) p = p.left;
            else p = p.right;
        }
        return p != null ? p.val : null;
    }

    // 指向第一次val出现节点的父节点，如果val未出现则指向插入位置的父节点
    // 如果val出现在root位置上，或者val应当在root位置插入，则指向NIL
    @SuppressWarnings("unchecked")
    private Node<T> searchParent(T val) {
        Node<T> p = root;
        Node<T> parent = NIL;

        while(p != null && comparator.compare(p.val, val) != 0) {
            parent = p;
            if(comparator.compare(p.val, val) > 0) p = p.left;
            else p = p.right;
        }
        return parent;
    }

    public void delete(T val) {
        Node<T> parent = searchParent(val);
        Node<T> p = null;
        // 这个地方是否存在问题？
        if(parent == NIL) {
            root = null;
            return;
        }

        int DIR = NONED;

        if(parent.left != null && comparator.compare(parent.val, val) == 0) {
            p = parent.left;
            DIR = LEFT;
        } else if(parent.right != null) {
            p = parent.right;
            DIR = RIGHT;
        }

        if(p == null) return;

        if(p.left == null) {
            connectParentAndChild(parent, p.right, DIR);
        } else if(p.right == null) {
            connectParentAndChild(parent, p.left, DIR);
        } else {
            Node<T> y = p;
            Node<T> father = null;
            while(y.left != null) {
                father = y;
                y = y.left;
            }
            if(father == null) {
                y.left = p.left;
            } else {
                Node<T> x = y.right;
                Node<T> r = p.right;
                Node<T> l = p.left;
                father.left = x;
                y.left = l;
                y.right = r;
            }
            connectParentAndChild(parent, y, DIR);
        }
    }

    private void connectParentAndChild(Node<T> parent, Node<T> child, int DIR) {
        if(DIR == LEFT)
            parent.left = child;
        if(DIR == RIGHT) parent.right = child;
    }


}
