package Sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 希尔排序
 *
 * 希尔排序通过选择不同的步长间隔，对通过步长选择出的数组元素进行多次排序，
 * 当步长最终为 1 时完成对数组的排序
 *
 * @author igaozp
 * @since 2017-7-1
 * @version 1.0
 */
public class Shell {
    /**
     * 希尔排序
     *
     * @param a 需要排序的数组
     */
    public static void sort(Comparable[] a) {
        // 数组长度
        int N = a.length;
        // 步长间隔
        int h = 1;

        // 选择合适的初始步长间隔
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        // 通过不同的步长间隔对数组多次排序
        while (h >= 1) {
            // 以步长为间隔选择数组元素进行排序
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            // 减少步长间隔
            h = h / 3;
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
