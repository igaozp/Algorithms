package me.metatom.java.string;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 高位优先的字符串排序
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-21
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
     * @return 字符位置
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
     * @param a  排序的字符串数组
     * @param lo 排序的起始位置
     * @param hi 排序的结束位置
     * @param d  字符串排序的字符位置
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo + M) {
            insertion(a, lo, hi, d);
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

    /**
     * 插入排序
     *
     * @param a  需要排序的数组
     * @param lo 起始位置
     * @param hi 结束位置
     * @param d  字符位置
     */
    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    /**
     * 交换两个字符串
     *
     * @param a 字符串数组
     * @param i 交换的一个字符串下标
     * @param j 交换的另一个字符串下标
     */
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * 比较两个字符串的大小
     *
     * @param v 一个字符串
     * @param w 另一个字符串
     * @param d 比较的起始字符位置
     * @return {@code true} v < w
     * {@code false} v > w
     */
    private static boolean less(String v, String w, int d) {
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) {
                return true;
            }
            if (v.charAt(i) > w.charAt(i)) {
                return false;
            }
        }
        return v.length() < w.length();
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;
        sort(a);
        for (int i = 0; i < n; i++) {
            StdOut.println(a[i]);
        }
    }
}
