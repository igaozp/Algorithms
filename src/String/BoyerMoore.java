package String;

import edu.princeton.cs.algs4.StdOut;

/**
 * Boyer-Moore 字符串匹配算法
 *
 * @author igaozp
 * @since 2017-07-27
 * @version 1.0
 */
public class BoyerMoore {
    /**
     * 记录每个字符在模式中出现的最靠右的地方
     */
    private int[] right;
    /**
     * 匹配的字符串（模式）
     */
    private String pat;

    /**
     * 构造方法
     *
     * @param pat 匹配的模式
     */
    BoyerMoore(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        right = new int[R];

        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }

        for (int j = 0; j < M; j++) {
            right[pat.charAt(j)] = j;
        }
    }

    /**
     * 字符串查找
     *
     * @param txt 用于查找字符串的文本
     * @return 查找到的字符串下标
     */
    public int search(String txt) {
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = j - right[txt.charAt(i + j)];
                    if (skip < 1) {
                        skip = 1;
                    }
                    break;
                }
            }
            if (skip == 0) {
                return i;
            }
        }
        return N;
    }

    /**
     * 测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        BoyerMoore boyerMoore = new BoyerMoore(pat);

        StdOut.println("text:   " + txt);
        int offset = boyerMoore.search(txt);
        StdOut.print("pattern: ");

        for (int i = 0; i < offset; i++) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
    }
}
