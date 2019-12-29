package io.metatom.find;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 二叉查找树
 *
 * @param <Key>   泛型类型
 * @param <Value> 泛型类型
 * @author igaozp
 * @version 1.0
 * @since 2017-07-05
 */
public class BST<Key extends Comparable<Key>, Value> {
    /**
     * 二叉树的根节点
     */
    private Node root;

    /**
     * 内部定义的节点类
     */
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;

        /**
         * 构造方法
         *
         * @param key  节点的键
         * @param val  节点的值
         * @param size 子节点的数量
         */
        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * 默认的无参的构造方法
     */
    public BST() {
    }

    /**
     * 检查二叉查找树是否为空
     *
     * @return {@code true} 二叉树为空
     * {@code false} 二叉树不为空
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 获取二叉树的节点数量
     *
     * @return 节点数量
     */
    public int size() {
        return size(root);
    }

    /**
     * 获取指定节点的子节点数量
     *
     * @param x 指定节点
     * @return 节点数量
     */
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    /**
     * 获取指定的键的值
     *
     * @param key 指定的键
     * @return 指定键的值
     */
    public Value get(Key key) {
        return get(root, key);
    }

    /**
     * 在指定节点下获取指定键的值
     *
     * @param x   指定的节点
     * @param key 指定的键
     * @return 指定键的值
     */
    private Value get(Node x, Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) {
            return null;
        }

        // 递归查找相应的键
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    /**
     * 向二叉树中添加新的节点
     *
     * @param key 节点的键
     * @param val 节点的值
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    /**
     * 在指定节点下添加新的节点
     *
     * @param x   指定的节点
     * @param key 新节点的键
     * @param val 新节点的值
     * @return 添加节点的指定节点
     */
    public Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, 1);
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    /**
     * 删除二叉树中的最小节点
     */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = deleteMin(root);
        assert check();
    }

    /**
     * 在指定节点下删除最小的节点
     *
     * @param x 指定的节点
     * @return 删除后的指定节点
     */
    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * 获取二叉函数中最小元素的键
     *
     * @return 最小元素的键
     */
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        return min(root).key;
    }

    /**
     * 在指定节点下获取最小元素的节点
     *
     * @param x 指定的节点
     * @return 最小元素的节点
     */
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    /**
     * 删除二叉树中的最大节点
     */
    public void deleteMax() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Symbol table underflow");
        }
        root = deleteMax(root);
        assert check();
    }

    /**
     * 删除二叉树中指定节点下的最大节点
     *
     * @param x 制定的节点
     * @return 删除后的指定节点
     */
    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * 获取二叉函数中最大元素的键
     *
     * @return 最大元素的键
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls max() with empty symbol table");
        }
        return max(root).key;
    }

    /**
     * 在指定节点大获取最小元素的节点
     *
     * @param x 指定的节点
     * @return 最大元素的节点
     */
    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    /**
     * 在二叉树中获取最大的不大于指定键的节点的键
     *
     * @param key 指定的键
     * @return 不大于指定键的键
     */
    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table");
        }

        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    /**
     * 在指定的节点下获取最大的不大于指定键的节点
     *
     * @param x   指定节点
     * @param key 指定键
     * @return 最大的节点
     */
    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return floor(x.left, key);
        }
        Node t = floor(x.right, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    /**
     * 在二叉树中获取最小的不小于指定键的节点的键
     *
     * @param key 指定的键
     * @return 不小于指定键的键
     */
    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("calls ceiling() with empty symbol table");
        }

        Node x = ceiling(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    /**
     * 在指定的节点下获取最小的不小于指定键的节点
     *
     * @param x   指定节点
     * @param key 指定键
     * @return 最小的节点
     */
    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp > 0) {
            return ceiling(x.right, key);
        }
        Node t = ceiling(x.left, key);
        if (t != null) {
            return t;
        } else {
            return x;
        }
    }

    /**
     * 在二叉树中查找指定大小的子树
     *
     * @param k 子树的大小
     * @return 子树的键
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }
        return select(root, k).key;
    }

    /**
     * 在指定节点下根据子树大小查找相应的节点
     *
     * @param x 指定的节点
     * @param k 子树的大小
     * @return 查找到的节点
     */
    private Node select(Node x, int k) {
        if (x == null) {
            return null;
        }
        int t = size(x.left);
        if (t > k) {
            return select(x.left, k);
        } else if (t < k) {
            return select(x.right, k - t - 1);
        } else {
            return x;
        }
    }

    /**
     * 根据键查找相应节点子树的节点数量
     *
     * @param key 查找的键
     * @return 节点数量
     */
    public int rank(Key key) {
        return rank(key, root);
    }

    /**
     * 在指定节点下通过键查找节点的子节点数量
     *
     * @param key 指定的键
     * @param x   指定的节点
     * @return 节点数量
     */
    private int rank(Key key, Node x) {
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(key, x.left);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }
    }

    /**
     * 根据键删除相应的节点
     *
     * @param key 需要删除的节点的键
     */
    public void delete(Key key) {
        root = delete(root, key);
    }

    /**
     * 在指定节点下根据键删除节点
     *
     * @param x   指定的节点
     * @param key 指定的键
     * @return 删除后的指定节点
     */
    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * 获取二叉树中所有的键
     *
     * @return 所有键的集合
     */
    public Iterable<Key> keys() {
        if (isEmpty()) {
            return new Queue<>();
        }
        return keys(min(), max());
    }

    /**
     * 根据键的范围获取范围内的键
     *
     * @param lo 开始范围
     * @param hi 结束范围
     * @return 包含键的队列
     */
    private Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }

        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    /**
     * 在指定节点和范围下获取所有键的集合
     *
     * @param x     指定的节点
     * @param queue 存储键的队列
     * @param lo    开始范围
     * @param hi    结束范围
     */
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) {
            return;
        }
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0) {
            keys(x.left, queue, lo, hi);
        }
        if (cmpLo <= 0 && cmpHi >= 0) {
            queue.enqueue(x.key);
        }
        if (cmpHi > 0) {
            keys(x.right, queue, lo, hi);
        }
    }

    /**
     * 获取二叉树的高度
     *
     * @return 二叉树的高度
     */
    public int height() {
        return height(root);
    }

    /**
     * 获取指定节点二叉树的盖度
     *
     * @param x 指定的节点
     * @return 二叉树的高度
     */
    private int height(Node x) {
        if (x == null) {
            return -1;
        }
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * 二叉树层次遍历
     *
     * @return 层次遍历序列
     */
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<>();
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
                continue;
            }
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    /**
     * 检查 BST 的完整性
     *
     * @return {@code true} 完整
     * {@code false} 不完整
     */
    private boolean check() {
        if (!isBST()) {
            StdOut.println("Not in symmetric order");
        }
        if (!isSizeConsistent()) {
            StdOut.println("Subtree counts not consistent");
        }
        if (!isRankConsistent()) {
            StdOut.println("Ranks not consistent");
        }
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    /**
     * 检查该树是否是二叉树
     *
     * @return {@code true} 是二叉树
     * {@code false} 不是二叉树
     */
    private boolean isBST() {
        return isBST(root, null, null);
    }

    /**
     * 检查指定节点下是否是二叉树
     *
     * @param x   指定的节点
     * @param min 最小范围
     * @param max 最大范围
     * @return {@code true} 是二叉树
     * {@code false} 不是二叉树
     */
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) {
            return true;
        }

        if (min != null && x.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) <= 0) {
            return false;
        }

        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    /**
     * 检查二叉树的大小是否一致
     *
     * @return {@code true} 一致
     * {@code false} 不一致
     */
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    /**
     * 检查指定节点下的二叉树的大小是否一致
     *
     * @param x 指定的节点
     * @return {@code true} 一致
     * {@code false} 不一致
     */
    private boolean isSizeConsistent(Node x) {
        if (x == null) {
            return true;
        }
        if (x.size != size(x.left) + size(x.right) + 1) {
            return false;
        }
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    /**
     * 检测 {@code rank()} 的一致性
     *
     * @return {@code true} 一致
     * {@code false} 不一致
     */
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (Key key : keys()) {
            if (key.compareTo(select(rank(key))) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * BST 的单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        BST<String, Integer> bst = new BST<>();

        int size;
        Scanner scanner = new Scanner(System.in);
        size = scanner.nextInt();

        for (int i = 0; i < size; i++) {
            String key = StdIn.readString();
            bst.put(key, i);
        }

        for (String s : bst.keys()) {
            System.out.println(s + " " + bst.get(s));
        }
    }
}
