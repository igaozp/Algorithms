package string;

/**
 * 三向字符串快速排序
 *
 * 根据键的首字母进行三向切分，仅在中间子数组中的下一个字符继续递归排序
 *
 * @author igaozp
 * @since 2017-07-24
 * @version 1.0
 */
public class Quick3String {
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
     * 字符串数组排序
     *
     * @param a 字符串数组
     */
    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    /**
     * 字符串数组排序
     *
     * @param a 字符串数组
     * @param lo 排序的开始下标
     * @param hi 排序的结束下标
     * @param d 字符串的排序位置
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo) {
            return;
        }
        int lt = lo;
        int gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v) {
                exch(a, lt++, i++);
            } else if (t > v) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }

        sort(a, lo, lt - 1, d);
        if (v >= 0) {
            sort(a, lt, gt, d + 1);
        }
        sort(a, gt + 1, hi, d);
    }

    /**
     * 交换字符串数组的两个字符串
     *
     * @param a 字符串数组
     * @param i 字符串
     * @param j 字符串
     */
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
