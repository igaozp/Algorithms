package me.metatom.java.string;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 基于三向单词查找树的符号表
 *
 * @param <Value> 泛型类型
 * @author igaozp
 * @version 1.0
 * @since 2017-07-26
 */
public class TST<Value> {
    /**
     * 根节点
     */
    private Node<Value> root;
    /**
     * 符号表的大小
     */
    private int n;

    /**
     * 构造方法
     */
    public TST() {
    }

    /**
     * 获取符号表的大小
     *
     * @return 符号表的大小
     */
    public int size() {
        return n;
    }

    /**
     * 内部定义的节点类
     */
    private class Node<Value> {
        // 字符
        char c;

        // 左中右子树
        Node left;
        Node mid;
        Node right;

        // 相关联的值
        Value val;
    }

    /**
     * 检查指定的 key 是否在符号表中
     *
     * @param key 指定的 key
     * @return {@code true} 在符号表表中
     * {@code false} 不在符号表中
     */
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * 根据字符串查找相关联的值
     *
     * @param key 用字符串表示的键
     * @return 相关联的值
     */
    public Value get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if (key.length() == 0) {
            throw new IllegalArgumentException("key must have length >= 1");
        }
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.val;
    }

    /**
     * 在指定节点下查找字符串相关联的节点
     *
     * @param x   指定的节点
     * @param key 用字符串表示的键
     * @param d   记录查找字符串的字符位置
     * @return 查找到的节点
     */
    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    /**
     * 向树中添加新的键值对
     *
     * @param key 键
     * @param val 值
     */
    public void put(String key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (!contains(key)) {
            n++;
        }
        root = put(root, key, val, 0);
    }

    /**
     * 在指定节点下添加键值对
     *
     * @param x   指定的节点
     * @param key 键
     * @param val 值
     * @param d   记录查找字符串的字符位置
     * @return 添加后的指定节点
     */
    private Node<Value> put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d + 1);
        } else {
            x.val = val;
        }
        return x;
    }


    /**
     * 获取指定的字符串的最长前缀
     *
     * @param query 查询的字符串
     * @return 指定字符串的最长前缀
     */
    public String longestPrefixOf(String query) {
        if (query == null) {
            throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        }
        if (query.length() == 0) {
            return null;
        }
        int length = 0;
        Node<Value> x = root;
        int i = 0;
        while (x != null && i < query.length()) {
            char c = query.charAt(i);
            if (c < x.c) {
                x = x.left;
            } else if (c > x.c) {
                x = x.right;
            } else {
                i++;
                if (x.val != null) {
                    length = i;
                }
                x = x.mid;
            }
        }
        return query.substring(0, length);
    }

    /**
     * 获取符号表的 key 的合集
     *
     * @return key 的合集
     */
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    /**
     * 获取指定前缀的所有的 key
     *
     * @param prefix 指定的前缀
     * @return key 的合集
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        Queue<String> queue = new Queue<String>();
        Node<Value> x = get(root, prefix, 0);
        if (x == null) {
            return queue;
        }
        if (x.val != null) {
            queue.enqueue(prefix);
        }
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) {
            return;
        }
        collect(x.left, prefix, queue);
        if (x.val != null) {
            queue.enqueue(prefix.toString() + x.c);
        }
        collect(x.mid, prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }

    private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
        if (x == null) {
            return;
        }
        char c = pattern.charAt(i);
        if (c == '.' || c < x.c) {
            collect(x.left, prefix, i, pattern, queue);
        }
        if (c == '.' || c == x.c) {
            if (i == pattern.length() - 1 && x.val != null) {
                queue.enqueue(prefix.toString() + x.c);
            }
            if (i < pattern.length() - 1) {
                collect(x.mid, prefix.append(x.c), i + 1, pattern, queue);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        if (c == '.' || c > x.c) {
            collect(x.right, prefix, i, pattern, queue);
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // build symbol table from standard input
        TST<Integer> st = new TST<Integer>();
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

        StdOut.println("longestPrefixOf(\"shell\"):");
        StdOut.println(st.longestPrefixOf("shell"));
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
