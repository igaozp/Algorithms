package io.metatom.find;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import io.metatom.base.Queue;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 红黑二叉查找树
 * <p>
 * 红黑树是一种二叉树，且红链接均为左链接，没有任何一个节点同时和两条红色链接相连，
 * 该树是完美黑色平衡的，即任意链接到根节点的路径上的黑链接数量相同。
 *
 * @param <Key>   用作 key 的泛型类型
 * @param <Value> 用作 value 的泛型类型
 * @author igaozp
 * @version 1.1
 * @since 2017-07-07
 */
@SuppressWarnings({"unused", "DuplicatedCode"})
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    /**
     * 红黑树节点颜色，使用 true 代表红色，false 代表黑色
     */
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * 红黑树的根节点
     */
    private Node root;

    /**
     * 用于红黑树的内部节点
     */
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        // 子树中的节点总数
        private int size;
        // 父节点此节点的链接颜色
        private boolean color;

        /**
         * 节点的构造函数
         *
         * @param key   节点的键
         * @param val   节点的值
         * @param size  子树的节点总数
         * @param color 父节点指向的颜色
         */
        Node(Key key, Value val, int size, boolean color) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.color = color;
        }
    }

    /**
     * 构造方法
     */
    public RedBlackBST() {
    }

    /**
     * 检查红黑树的节点数量
     *
     * @return 红黑树的节点数量
     */
    public int size() {
        return size(root);
    }

    /**
     * 检查指定节点的子节点的数量
     *
     * @param x 需要检查的节点
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
     * 检查红黑树是否为空
     *
     * @return {@code true} 红黑树为空
     * {@code false} 红黑树不为空
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 检查指定节点的颜色是否为红色
     *
     * @param x 需要检查的节点
     * @return {@code true} 节点为红色
     * {@code false} 节点为黑色
     */
    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    /**
     * 节点左旋
     *
     * @param h 需要旋转的节点
     * @return 旋转后的节点
     */
    public Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 节点右旋
     *
     * @param h 需要旋转的节点
     * @return 旋转后的节点
     */
    public Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 转换链接的颜色
     *
     * @param h 需要转换的节点
     */
    public void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 向红黑树中添加节点
     *
     * @param key 添加的节点的键
     * @param val 添加的节点的值
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
    }

    /**
     * 向红黑树的指定节点下添加新的节点
     *
     * @param h   指定的节点
     * @param key 添加节点的键
     * @param val 添加节点的值
     * @return 添加节点的指定节点
     */
    private Node put(Node h, Key key, Value val) {
        if (h == null) {
            return new Node(key, val, 1, RED);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = put(h.left, key, val);
        } else if (cmp > 0) {
            h.right = put(h.right, key, val);
        } else {
            h.val = val;
        }

        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * 删除指定的节点
     *
     * @param key 指定的节点
     */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (!contains(key)) {
            return;
        }

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, key);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 在红黑树的指定节点下删除相应的节点
     *
     * @param h   指定的节点
     * @param key 删除节点的键
     * @return 删除节点的指定节点
     */
    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }

            if (key.compareTo(h.key) == 0) {
                h.val = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }

        return balance(h);
    }

    /**
     * 删除红黑树中最小的节点
     */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        }

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 删除指定节点下最小的节点
     *
     * @param h 指定的节点
     * @return 删除节点的指定节点
     */
    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * 假设节点 h 为红色，h.left 和 h.left.left 都是黑色，
     * 将节点 h.left 或 h.left 的子节点变红
     *
     * @param h 指定的节点
     * @return 转换后指定的节点
     */
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    /**
     * 假设节点 h 为红色，h.right 和 h.right.right 都是黑色，
     * 将 h.right 或 h.right 的子节点变红
     *
     * @param h 指定的节点
     * @return 转换后指定的节点
     */
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (!isRed(h.left.left)) {
            h = rotateRight(h);
        }
        return h;
    }

    /**
     * 删除红黑树中最大的节点
     */
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("BST underflow");
        }

        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 删除指定节点下最大的节点
     *
     * @param h 指定的节点
     * @return 删除后指定的节点
     */
    private Node deleteMax(Node h) {
        if (isRed(h.left)) {
            h = rotateRight(h);
        }
        if (h.right == null) {
            return null;
        }
        if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }

    /**
     * 将指定节点的子树重新平衡
     *
     * @param h 需要平衡的节点
     * @return 平衡后的节点
     */
    private Node balance(Node h) {
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * 获取红黑树最小元素的键
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
     * 获取指定元素下最小的节点
     *
     * @param h 指定的元素
     * @return 指定元素的最小节点
     */
    private Node min(Node h) {
        if (h.left == null) {
            return h;
        }
        return min(h.left);
    }

    /**
     * 获取红黑树最大元素的键
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
     * 获取指定节点下最大的节点
     *
     * @param h 指定的节点
     * @return 指定元素的最大节点
     */
    private Node max(Node h) {
        if (h.right == null) {
            return h;
        }
        return max(h.right);
    }

    /**
     * 通过指定的键从红黑树中获取相应的值
     *
     * @param key 需要获取值的键
     * @return 相应键的值
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }

        return get(root, key);
    }

    /**
     * 从指定节点下根据键获取相应的值
     *
     * @param h   指定的节点
     * @param key 需要获取值的键
     * @return 相应键的值
     */
    private Value get(Node h, Key key) {
        if (h == null) {
            return null;
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            return get(h.left, key);
        } else if (cmp > 0) {
            return get(h.right, key);
        } else {
            return h.val;
        }
    }

    /**
     * 检查指定的键是否存在
     *
     * @param key 要查找的键
     * @return {@code true} 存在
     * {@code false} 不存在
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * 获取二叉树的高度
     *
     * @return 二叉树的高度
     */
    private int height() {
        return height(root);
    }

    /**
     * 获取指定节点的二叉树高度
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
     * 获取二叉树中不大于指定键的最大节点
     *
     * @param key 指定的节点
     * @return 查找到的键
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
        } else {
            return x.key;
        }
    }

    /**
     * 查找指定节点下最大的不大于该键的节点
     *
     * @param x   指定的节点
     * @param key 指定的键
     * @return 查找到的节点
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
        return Objects.requireNonNullElse(t, x);
    }

    /**
     * 获取二叉树中不小于指定键的最小节点的键
     *
     * @param key 指定的键
     * @return 查找到的键
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
        } else {
            return x.key;
        }
    }

    /**
     * 在指定节点下获取不小于的指定键的最小节点
     *
     * @param x   指定的节点
     * @param key 指定的键
     * @return 查找到的节点
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
        return Objects.requireNonNullElse(t, x);
    }

    /**
     * 在二叉树中查找指定大小的子树
     *
     * @param k 指定的大小
     * @return 查找到的节点
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }
        Node x = select(root, k);
        return x.key;
    }

    /**
     * 在指定节点下查找指定大小的二叉树
     *
     * @param x 指定的节点
     * @param k 指定的大小
     * @return 查找到的节点
     */
    private Node select(Node x, int k) {
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
     * 获取二叉树中小于指定键的节点数量
     *
     * @param key 指定的键
     * @return 节点数量
     */
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        return rank(key, root);
    }

    /**
     * 在指定节点下查找小于指定键的节点数量
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
     * 查找二叉树中所有的键
     *
     * @return 键的集合
     */
    public Iterable<Key> keys() {
        if (isEmpty()) {
            return new Queue<>();
        }
        return keys(min(), max());
    }

    /**
     * 查找指定范围的键
     *
     * @param lo 开始范围
     * @param hi 结束范围
     * @return 键的集合
     */
    public Iterable<Key> keys(Key lo, Key hi) {
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
     * 在指定节点下将指定范围的键存入队列中
     *
     * @param x     指定的节点
     * @param queue 存入的队列
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
     * 检查是否是 BST
     *
     * @return {@code true} 是 BST
     * {@code false} 不是 BST
     */
    private boolean isBST() {
        return isBST(root, null, null);
    }

    /**
     * 在指定节点下检查指定范围的二叉树是否是 BST
     *
     * @param x   指定的节点
     * @param min 起始范围
     * @param max 结束范围
     * @return {@code true} 是 BST
     * {@code false} 不是 BST
     */
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) {
            return true;
        }
        if (min != null && x.key.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && x.key.compareTo(max) >= 0) {
            return false;
        }
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    /**
     * 检查二叉树的大小是否稳定
     *
     * @return {@code true} 稳定
     * {@code false} 不稳定
     */
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    /**
     * 在指定节点下检查二叉树的大小是否稳定
     *
     * @param x 指定的节点
     * @return {@code true} 稳定
     * {@code false} 不稳定
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
     * 检查二叉树的顺序是否稳定
     *
     * @return {@code true} 稳定
     * {@code false} 不稳定
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
     * 检查该树是否是 2-3树
     *
     * @return {@code true} 是 2-3树
     * {@code false} 不是 2-3树
     */
    private boolean is23() {
        return is23(root);
    }

    /**
     * 在指定的节点下检查二叉树是否是 2-3树
     *
     * @param x 指定的节点
     * @return {@code true} 是 2-3树
     * {@code false} 不是 2-3树
     */
    private boolean is23(Node x) {
        if (x == null) {
            return true;
        }
        if (isRed(x.right)) {
            return false;
        }
        if (x != root && isRed(x) && isRed(x.left)) {
            return false;
        }
        return is23(x.left) && is23(x.right);
    }

    /**
     * 检查二叉树是否平衡
     *
     * @return {@code true} 平衡
     * {@code false} 不平衡
     */
    private boolean isBalanced() {
        int black = 0;
        Node x = root;
        while (x != null) {
            if (!isRed(x)) {
                black++;
            }
            x = x.left;
        }
        return isBalanced(root, black);
    }

    /**
     * 检查指定节点到叶子节点的路径黑色的数量是否一致
     *
     * @param x     指定的节点
     * @param black 黑色连接的数量
     * @return {@code true}
     * {@code false}
     */
    private boolean isBalanced(Node x, int black) {
        if (x == null) {
            return black == 0;
        }
        if (!isRed(x)) {
            black--;
        }
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    /**
     * 红黑树的单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        RedBlackBST<String, Integer> st = new RedBlackBST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
        StdOut.println();
    }
}
