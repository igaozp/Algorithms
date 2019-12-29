package io.metatom.sort

import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.StdOut

/**
 * 选择排序
 *
 * 选择排序将数组分成前后两部分，通过不断选择后半部分最小的元素，并将元素交换到
 * 后半部分的第一个元素上，此时前半部分已经有序且均小于后半部分，最后完成整个数组的排序
 *
 * @author igaozp
 * @since 2017-09-01
 * @version 1.2
 */
class Selection {
    /**
     * 选择排序
     *
     * @param a 需要排序的数组
     */
    fun sort(a: Array<Comparable<Any>>) {
        val N = a.size
        for (i in 0 until N) {
            var min = i
            // 寻找后半部分最小的元素下标
            val j = i + 1
            while (j < N) {
                if (less(a[j], a[min])) {
                    min = j
                }
            }
            // 将后半部分最小的元素交换到后半部分第一个元素
            exch(a, i, min)
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
    fun show(a: Array<Comparable<Any>>) {
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
    fun isSorted(a: Array<Comparable<Any>>): Boolean {
        return (1..a.size).none { less(a[it], a[it - 1]) }
    }
}

/**
 * 测试排序算法
 *
 * @param args 命令行参数
 */
fun main(args: Array<String>) {
    val selection = Selection()
    val a = In.readStrings() as Array<Comparable<Any>>
    selection.sort(a)
    assert(selection.isSorted(a))
    selection.show(a)
}