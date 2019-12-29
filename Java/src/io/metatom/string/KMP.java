package io.metatom.string;

import edu.princeton.cs.algs4.StdOut;

/**
 * KMP 字符串查找算法
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-27
 */
public class KMP {
    /**
     * 匹配的字符串
     */
    private String pat;
    /**
     * 有限状态自动机
     */
    private int[][] dfa;

    /**
     * KMP 构造方法
     *
     * @param pat 想要查找的字符串
     */
    public KMP(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;

        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            dfa[pat.charAt(j)][j] = j + 1;
            X = dfa[pat.charAt(j)][X];
        }
    }

    /**
     * KMP 查找
     *
     * @param txt 用作搜索的文本
     * @return 查找到的字符串下标
     */
    public int search(String txt) {
        int i, j, N = txt.length(), M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
        }
        if (j == M) {
            return i - M;
        } else {
            return N;
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        KMP kmp = new KMP(pat);

        StdOut.println("text:   " + txt);
        int offset = kmp.search(txt);
        StdOut.print("pattern: ");

        for (int i = 0; i < offset; i++) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
    }
}
