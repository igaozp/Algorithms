package Sort

import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.StdOut

/**
 * 插入排序
 *
 * 插入排序算法将数组中的元素插入到已经排序
 * 好的序列的合适的位置上，来完成排序
 *
 * @author igaozp
 * @since 2017-08-31
 * @version 1.0
 */
class KTInsertion {
    /**
     * 插入排序
     *
     * @param a 需要排序的数组
     */
    fun sort(a: Array<Comparable<Any>>) {
        val N = a.size
        // 遍历整个数组
        for (i in 1..N) {
            // 查找下标 i 之前的元素，如果小于下标 i 的元素则交换两个元素
            var j = i
            while (j > 0 && less(a[j], a[j - 1])) {
                exch(a, j, j - 1)
                j--
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
    private fun less(v: Comparable<Any>, w: Comparable<Any>): Boolean {
        return v.compareTo(w) < 0
    }

    /**
     * 交换元素
     *
     * @param a 需要交换操作的数组
     * @param i 交换的第一个元素的下标
     * @param j 交换的第二个元素的下标
     */
    private fun exch(a: Array<Comparable<Any>>, i: Int, j: Int) {
        val t = a[i]
        a[i] = a[j]
        a[j] = t
    }

    /**
     * 显示数组元素
     *
     * @param a 需要显示的数组
     */
    private fun show(a: Array<Comparable<Any>>) {
        for (i in a) {
            StdOut.print("$i ")
        }
        StdOut.println()
    }

    /**
     * 检查数组是否已经排序
     *
     * @param a 需要检查的数组
     * @return `true` 已经排序
     *         `false` 没有排序
     */
    private fun isSorted(a: Array<Comparable<Any>>): Boolean {
        return (1 until a.size).none { less(a[it], a[it - 1]) }
    }

    /**
     * 测试排序算法
     *
     * @param args 命令行参数
     */
    fun main(args: Array<String>) {
        @Suppress("UNCHECKED_CAST")
        val a = In.readStrings() as Array<Comparable<Any>>
        sort(a)
        assert(isSorted(a))
        show(a)
    }
}