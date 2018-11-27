package me.metatom.java.string;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

/**
 * LZW 压缩
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-30
 */
public class LZW {
    /**
     * 输入的字符数
     */
    private static final int R = 256;
    /**
     * 编码总数
     */
    private static final int L = 4096;
    /**
     * 编码宽度
     */
    private static final int W = 12;

    /**
     * 压缩
     */
    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();

        for (int i = 0; i < R; i++) {
            st.put("" + (char) i, i);
        }

        int code = R + 1;

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);
            BinaryStdOut.write(st.get(s), W);

            int t = s.length();
            if (t < input.length() && code < L) {
                st.put(input.substring(0, t + 1), code++);
            }
            input = input.substring(t);
        }

        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    /**
     * 解码
     */
    public static void expand() {
        String[] st = new String[L];
        int i;

        for (i = 0; i < R; i++) {
            st[i] = "" + (char) i;
        }
        st[i++] = " ";

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) {
                break;
            }

            String s = st[codeword];
            if (i == codeword) {
                s = val + val.charAt(0);
            }

            if (i < L) {
                st[i++] = val + s.charAt(0);
            }
            val = s;
        }
        BinaryStdOut.close();
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
