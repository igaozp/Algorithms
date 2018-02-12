package string;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * 哈夫曼压缩
 *
 * @author igaozp
 * @version 1.1
 * @since 2017-07-29
 */
public class Huffman {
    /**
     * ASCII 字母表
     */
    private static int R = 256;

    /**
     * 内部定义的单词查找树节点类
     */
    private static class Node implements Comparable<Node> {
        /**
         * 被编码的字符
         */
        private char ch;
        /**
         * 字符出现的次数
         */
        private int freq;
        /**
         * 节点的左右子节点
         */
        private final Node left, right;

        /**
         * 内部类的构造方法
         *
         * @param ch    被编码的字符
         * @param freq  字符出现的次数
         * @param left  左节点
         * @param right 右节点
         */
        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        /**
         * 检查该节点是否是叶子节点
         *
         * @return {@code true} 是叶子节点
         * {@code false} 不是叶子节点
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * 与其他节点对比出现的次数
         *
         * @param that 其他的节点
         * @return 出现次数之差
         */
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    /**
     * 解码
     */
    public static void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt();

        for (int i = 0; i < N; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                if (BinaryStdIn.readBoolean()) {
                    x = x.right;
                } else {
                    x = x.left;
                }
            }
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.close();
    }

    /**
     * 使用单词查找树构造编译表
     *
     * @param root 单词查找树的根节点
     * @return 构造完成的编译表
     */
    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    /**
     * 使用单词查找树构造编译表
     *
     * @param st 编译表
     * @param x  开始的节点
     * @param s  初始编码
     */
    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    /**
     * 构造哈夫曼编码的查找树
     *
     * @param freq 字符出现次数表
     * @return 树中的最小节点
     */
    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<Node>();

        for (char c = 0; c < R; c++) {
            if (freq[c] > 0) {
                pq.insert(new Node(c, freq[c], null, null));
            }
        }

        while (pq.size() > 1) {
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.insert(parent);
        }

        return pq.delMin();
    }

    /**
     * 将查找树写为比特字符串
     *
     * @param x 开始节点
     */
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);

            return;
        }

        BinaryStdOut.write(false);

        writeTrie(x.left);
        writeTrie(x.right);
    }

    /**
     * 从比特流的先序表示中重建单词查找树
     *
     * @return 查找树的根节点
     */
    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) {
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        }
        return new Node('\0', 0, readTrie(), readTrie());
    }

    /**
     * 压缩
     */
    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        int[] freq = new int[R];

        for (char c : input) {
            freq[c]++;
        }

        Node root = buildTrie(freq);
        String[] st = new String[R];
        buildCode(st, root, "");
        writeTrie(root);
        BinaryStdOut.write(input.length);

        for (char c : input) {
            String code = st[c];
            for (int j = 0; j < code.length(); j++) {
                if ("1".equals(code.charAt(j))) {
                    BinaryStdOut.write(true);
                } else {
                    BinaryStdOut.write(false);
                }
            }
            BinaryStdOut.close();
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        switch (args[0]) {
            case "-":
                compress();
                break;
            case "+":
                expand();
                break;
            default:
                throw new IllegalArgumentException("Illegal command line argument");
        }
    }
}
