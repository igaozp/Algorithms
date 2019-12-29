package io.metatom.sort;

/**
 * 自底向上的归并排序
 * <p>
 * 自底向上的归并排序通过把多个小的数组归并排序，逐渐完成整个数组的排序
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-7-2
 */
public class MergeBU {
    /**
     * 用于暂存的数组
     */
    private static Comparable[] aux;

    /**
     * 归并排序
     *
     * @param a 需要排序的数组
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                // 归并数组
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
    }

    /**
     * 将两个有序数组归并为一个有序的数组
     *
     * @param a   需要归并操作的数组
     * @param lo  归并数组的开始下标
     * @param mid 归并数组的中间下标
     * @param hi  归并数组的结束下标
     */
    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;

        // 将 a[lo..hi] 复制到 aux[lo..hi]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // 归并回到 a[lo..hi]
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
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
}
