package Sort

/**
 * 自底向上的归并排序
 *
 * 自底向上的归并排序通过把多个小的数组归并排序，逐渐完成整个数组的排序
 *
 * @author igaozp
 * @since 2017-09-02
 * @version 1.0
 */
class KTMergeBU {
    /**
     * 用于暂存的数组
     */
    private var aux: Array<Comparable<Any>>? = null

    /**
     * 归并排序
     *
     * @param a 需要排序的数组
     */
    fun sort(a: Array<Comparable<Any>>) {
        val N = a.size
        aux = Array(N)
        var sz = 1
        while (sz < N) {
            var lo = 0
            while (lo < (N - sz)) {
                // 归并数组
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1))
                lo += sz + sz
            }
            sz += sz
        }
    }

    /**
     * 用于初始化数组的辅助函数
     *
     * @param size 数组大小
     */
    private fun Array(size: Int): Array<Comparable<Any>> = Array(size)

    /**
     * 将两个有序数组归并为一个有序的数组
     *
     * @param a 需要归并操作的数组
     * @param lo 归并数组的开始下标
     * @param mid 归并数组的中间下标
     * @param hi 归并数组的结束下标
     */
    private fun merge(a: Array<Comparable<Any>>, lo: Int, mid: Int, hi: Int) {
        var i = lo
        var j = mid + 1

        // 将 a[lo..hi] 复制到 aux[lo..hi]
        for (k in lo..(hi + 1)) {
            aux!![k] = a[k]
        }

        // 归并回到 a[lo..hi]
        for (k in lo..(hi + 1)) {
            when {
                i > mid -> a[k] = aux!![j++]
                j > hi -> a[k] = aux!![i++]
                less(aux!![j], aux!![i]) -> a[k] = aux!![j++]
                else -> a[k] = aux!![i++]
            }
        }
    }

    /**
     * 比较两个参数的大小
     *
     * @param v 比较的第一个参数
     * @param w 比较的第二个参数
     * @return `true` 第一个参数比第二个小
     *         `false` 第一个参数比第二个大
     */
    fun less(v: Comparable<Any>, w: Comparable<Any>): Boolean {
        return v < w
    }
}