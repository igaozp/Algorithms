package String;

import edu.princeton.cs.algs4.Insertion;

/**
 * 高位优先的字符串排序
 *
 * @author igaozp
 * @since 2017-07-21
 * @version 1.0
 */
public class MSD {
    /**
     * 基数
     */
    private static int R = 256;
    /**
     * 小数组的切换阈值
     */
    private static final int M = 15;
    /**
     * 数组分类的辅助数组
     */
    private static String[] aux;

    /**
     * 获取字符串指定位置的字符
     *
     * @param s 字符串
     * @param d 指定的位置
     * @return 字符
     */
    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    /**
     * 字符串排序
     *
     * @param a 排序的字符串数组
     */
    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    /**
     * 字符串排序
     *
     * @param a 排序的字符串数组
     * @param lo 排序的起始位置
     * @param hi 排序的结束位置
     * @param d 字符串排序的字符位置
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + M) {
            Insertion.sort(a, lo, hi, d);
            return;
        }

        int[] count = new int[R + 2];

        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }

        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }

        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        System.arraycopy(aux, 0, a, lo, hi + 1 - lo);

        for (int r = 0; r < R; r++) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }
}
