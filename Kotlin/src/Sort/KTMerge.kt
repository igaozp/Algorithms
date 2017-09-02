package Sort

/**
 * 自顶向下的归并排序
 *
 * 自定向下的归并排序通过递归把数组不断分割成多个小的连续数组
 * 再将多个数组分别排序，最后将多个有序数组合并成一个有序的数组
 *
 * @author igaozp
 * @since 2017-09-01
 * @version 1.2
 */
class KTMerge {
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
        aux = Array(a.size)
        sort(a, 0, a.size - 1)
    }

    /**
     * 用于初始化数组的函数
     *
     * @param size 数组大小
     */
    private fun Array(size: Int): Array<Comparable<Any>> = Array(size)

    /**
     * 内部用于递归的归并排序
     *
     * @param a 需要排序的数组
     * @param lo 排序数组的最小下标
     * @param hi 排序数组的最大下标
     */
    private fun sort(a: Array<Comparable<Any>>, lo: Int, hi: Int) {
        if (hi <= lo) return
        val mid = lo + (hi - lo) / 2
        // 将数组分为两部分，对两部分分别排序
        sort(a, lo, mid)
        sort(a, mid + 1, hi)
        // 归并操作
        merge(a, lo, mid, hi)
    }

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
        for (k in lo..hi) {
            aux!![k] = a[k]
        }

        // 归并回到 a[lo..hi]
        for (k in lo..hi) {
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
    private fun less(v: Comparable<Any>, w: Comparable<Any>): Boolean = v < w
}