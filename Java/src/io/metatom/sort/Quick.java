package io.metatom.sort;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 快速排序
 * <p>
 * 快速排序将一个数组分成两个子数组，将两部分分别排序，
 * 并递归地执行这样的步骤完成对整个数组的排序
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-7-2
 */
@SuppressWarnings({"unused"})
public class Quick {
    /**
     * 快速排序
     *
     * @param a 需要排序的数组
     */
    public static void sort(Comparable[] a) {
        // 打乱原有数组的顺序，消除对输入的依赖
        StdRandom.shuffle(a);
        // 快速排序
        sort(a, 0, a.length - 1);
    }

    /**
     * 内部定义的快速排序
     *
     * @param a  需要排序的数组
     * @param lo 排序数组的开始下标
     * @param hi 排序数组的结束下标
     */
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        // 将数组切分为两部分，获得切分下标
        int j = partition(a, lo, hi);
        // 将左半部分排序
        sort(a, lo, j - 1);
        // 将右半部分排序
        sort(a, j + 1, hi);
    }

    /**
     * 将数组切分，获得切分下标
     *
     * @param a  需要切分的数组
     * @param lo 切分数组的开始下标
     * @param hi 切分数组的结束下标
     * @return 切分元素的下标
     */
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            // 在 a[lo..hi] 内寻找大于 v 的元素下标
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            // 在 a[lo..hi] 内寻找小于 v 的元素下标
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            // 将两个元素交换
            exch(a, i, j);
        }
        // 将用于切分数组的比较元素变量 v 放到正确的位置上
        exch(a, lo, j);
        // 返回切分下标
        return j;
    }

    /**
     * 比较两个参数的大小
     *
     * @param v 比较的第一个参数
     * @param w 比较的第二个参数
     * @return {@code true} 第一个参数比第二个小
     * {@code false} 第一个参数比第二个大
     */
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * 交换元素
     *
     * @param a 需要交换操作的数组
     * @param i 交换的第一个元素的下标
     * @param j 交换的第二个元素的下标
     */
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
