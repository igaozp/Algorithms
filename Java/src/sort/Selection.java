package sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 选择排序
 *
 * 选择排序将数组分成前后两部分，通过不断选择后半部分最小的元素，并将元素交换到
 * 后半部分的第一个元素上，此时前半部分已经有序且均小于后半部分，最后完成整个数组的排序
 *
 * @author igaozp
 * @since 2017-7-1
 * @version 1.0
 */
public class Selection {
    /**
     * 选择排序
     *
     * @param a 需要排序的数组
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            // 寻找后半部分最小的元素下标
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            // 将后半部分最小的元素交换到后半部分第一个元素
            exch(a, i, min);
        }
    }

    /**
     * 比较两个参数的大小
     *
     * @param v 比较的第一个参数
     * @param w 比较的第二个参数
     * @return {@code true} 第一个参数比第二个小
     *         {@code false} 第一个参数比第二个大
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

    /**
     * 显示数组元素
     *
     * @param a 需要显示的数组
     */
    private static void show(Comparable[] a) {
        for (Comparable i : a) {
            StdOut.print(i + " ");
        }
        StdOut.println();
    }

    /**
     * 检查数组是否已经排序
     *
     * @param a 需要检查的数组
     * @return {@code true} 已经排序
     *         {@code false} 没有排序
     */
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            // 如果存在降序的序列则数组没有排序
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 测试排序算法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String[] a = In.readStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }
}
