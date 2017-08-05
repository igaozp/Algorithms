package Sort;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 插入排序
 *
 * 插入排序算法将数组中的元素插入到已经排序
 * 好的序列的合适的位置上，来完成排序
 *
 * @author igaozp
 * @since 2017-7-1
 * @version 1.0
 */
public class Insertion {
    public static void sort(Comparable[] a) {
        int N = a.length;
        // 遍历整个数组
        for (int i = 1; i < N; i++) {
            // 查找下标 i 之前的元素，如果小于下标 i 的元素则交换两个元素
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
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
     * 显示相应的数组元素
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
