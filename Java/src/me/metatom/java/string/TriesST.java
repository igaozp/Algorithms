package me.metatom.java.string;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieST;

/**
 * 基于单词查找树的符号表
 *
 * @param <Value> 泛型类型
 * @author igaozp
 * @version 1.0
 * @since 2017-07-25
 */
public class TriesST<Value> {
    /**
     * 基数
     */
    private static final int R = 256;
    /**
     * 根节点
     */
    private Node root;
    /**
     * key 的数量
     */
    private int n;

    /**
     * 内部定义的节点类
     */
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    /**
     * 构造方法
     */
    private TriesST() {
    }

    /**
     * 获取键获取值
     *
     * @param key 指定的键
     * @return 值
     */
    public Value get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.val;
    }

    /**
     * 查找指定节点下与 key 相关联的值
     *
     * @param x   指定的节点
     * @param key 查找的键
     * @param d   用于选择单词树的分支，标记已选字符串长度
     * @return 相应的节点
     */
    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        // 标记的长度和查找长度相同
        if (d == key.length()) {
            return x;
        }

        // 获取下一个字符的分支
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    /**
     * 向单词树中添加新的字符串和相应的值
     *
     * @param key 用作查找符号的字符串
     * @param val 字符串对应的值
     */
    public void put(String key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        if (val == null) {
            delete(key);
        } else {
            root = put(root, key, val, 0);
        }
    }

    /**
     * 向指定节点下添加新的字符串和对应的值
     *
     * @param x   指定的节点
     * @param key 用作查找符号的字符串
     * @param val 字符串对应的值
     * @param d   用于选择单词树的分支，标记已选字符串长度
     * @return 插入后指定的节点
     */
    private Node put(Node x, String key, Value val, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    /**
     * 获取 key 的数量
     *
     * @return key 的数量
     */
    public int size() {
        return n;
    }

    /**
     * 符号表是否为空
     *
     * @return {@code true} 符号表为空
     * {@code false} 符号表不为空
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 获取符号表中所有的键
     *
     * @return 所有的键
     */
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /**
     * 查找指定前缀的键的集合
     *
     * @param pre 指定的前缀
     * @return 键的集合
     */
    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<>();
        collect(get(root, pre, 0), new StringBuilder(pre), q);
        return q;
    }

    /**
     * 在指定节点下获取指定前缀的键的集合
     *
     * @param x   指定的节点
     * @param pre 指定的前缀
     * @param q   存储键的队列
     */
    private void collect(Node x, StringBuilder pre, Queue<String> q) {
        if (x == null) {
            return;
        }
        if (x.val != null) {
            q.enqueue(pre.toString());
        }
        for (char c = 0; c < R; c++) {
            pre.append(c);
            collect(x.next[c], pre, q);
            pre.deleteCharAt(pre.length() - 1);
        }
    }

    /**
     * 在符号表中查找相匹配的键的集合
     *
     * @param pat 匹配的字符串
     * @return 键的集合
     */
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<>();
        collect(root, new StringBuilder(), pat, q);
        return q;
    }

    /**
     * 在指定的节点和前缀下，对指定的字符串进行匹配
     *
     * @param x   指定的节点
     * @param pre 指定的前缀
     * @param pat 匹配的字符串
     * @param q   存储匹配的字符串
     */
    private void collect(Node x, StringBuilder pre, String pat, Queue<String> q) {
        if (x == null) {
            return;
        }
        int d = pre.length();
        if (d == pat.length() && x.val != null) {
            q.enqueue(pre.toString());
        }
        if (d == pat.length()) {
            return;
        }

        char next = pat.charAt(d);
        for (char c = 0; c < R; c++) {
            if (next == '.' || next == c) {
                pre.append(c);
                collect(x.next[c], pre, pat, q);
                pre.deleteCharAt(pre.length() - 1);
            }
        }
    }

    /**
     * 查找指定字符串的最长前缀
     *
     * @param s 指定的字符串
     * @return 最长的前缀
     */
    public String longestPrefixOf(String s) {
        if (s == null) {
            throw new IllegalArgumentException("argument to longestPrefixOf is null");
        }
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    /**
     * 在指定节点下查找指定字符串的最长前缀
     *
     * @param x      指定的节点
     * @param s      指定的字符串
     * @param d      用于选择单词树的分支，标记已选字符串长度
     * @param length 前缀的长度
     * @return 最长前缀下标
     */
    private int search(Node x, String s, int d, int length) {
        if (x == null) {
            return length;
        }
        if (x.val != null) {
            length = d;
        }
        if (d == s.length()) {
            return length;
        }
        char c = s.charAt(d);
        return search(x.next[c], s, d + 1, length);
    }

    /**
     * 删除指定的字符串
     *
     * @param key 指定的字符串
     */
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    /**
     * 在指定的节点下删除指定的字符串
     *
     * @param x   指定的节点
     * @param key 指定的字符串
     * @param d   用于选择单词树的分支，标记已选字符串长度
     * @return 删除后指定的节点
     */
    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        if (x.val != null) {
            return x;
        }
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor")) {
            StdOut.println(s);
        }
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l.")) {
            StdOut.println(s);
        }
    }
}
