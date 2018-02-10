package sort;

/**
 * 自顶向下的归并排序
 *
 * 自定向下的归并排序通过递归把数组不断分割成多个小的连续数组，
 * 再将多个数组分别排序，最后将多个有序数组合并成一个有序的数组
 *
 * @author igaozp
 * @since 2017-7-2
 * @version 1.0
 */
public class Merge {
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
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    /**
     * 内部用于递归的归并排序
     *
     * @param a 需要排序的数组
     * @param lo 排序数组的最小下标
     * @param hi 排序数组的最大下标
     */
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        // 将数组分为两部分，对两部分分别排序
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        // 归并操作
        merge(a, lo, mid, hi);
    }

    /**
     * 将两个有序数组归并为一个有序的数组
     *
     * @param a 需要归并操作的数组
     * @param lo 归并数组的开始下标
     * @param mid 归并数组的中间下标
     * @param hi 归并数组的结束下标
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
     *         {@code false} 第一个参数比第二个大
     */
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
}
