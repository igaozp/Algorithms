package Sort

import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.StdOut

/**
 * 希尔排序
 *
 * 希尔排序通过选择不同的步长间隔，对通过步长选择出的数组元素进行多次排序，
 * 当步长最终为 1 时完成对数组的排序
 *
 * @author igaozp
 * @since 2017-09-01
 * @version 1.2
 */
class KTShell {
    /**
     * 希尔排序
     *
     * @param a 需要排序的数组
     */
    fun sort(a: Array<Comparable<Any>>) {
        // 数组长度
        val N = a.size
        // 步长间隔
        var h = 1

        // 选择合适的初始步长间隔
        while (h < N / 3) {
            h = 3 * h + 1
        }

        // 通过不同的步长间隔对数组多次排序
        while (h >= 1) {
            // 以步长为间隔选择数组元素进行排序
            (h until N).forEach { i ->
                val j = i
                while (j >= h && less(a[j], a[j - h])) {
                    exch(a, j, j - h)
                }
            }
            // 减少步长间隔
            h /= 3
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
        for (i in a) StdOut.print("$i ")
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
        return (1..a.size).none { less(a[it], a[it - 1]) }
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