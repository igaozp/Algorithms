package me.metatom.kotlin.string

/**
 * 高位优先的字符串排序
 *
 * @author igaozp
 * @since 2017-09-14
 * @version 1.0
 */
class MSD {
    /**
     * 基数
     */
    private var R = 256
    /**
     * 小数组的切换阈值
     */
    private var M = 15
    /**
     * 数组分类的辅助数组
     */
    private var aux: Array<String?>? = null

    /**
     * 获取字符串指定位置的字符
     *
     * @param s 字符串
     * @param d 指定的位置
     * @return 字符位置
     */
    private fun charAt(s: String, d: Int): Int = when {
        d < s.length -> s[d].toInt()
        else -> -1
    }

    /**
     * 字符串排序
     *
     * @param a 排序的字符串数组
     */
    fun sort(a: Array<String>) {
        val N = a.size
        aux = arrayOfNulls(N)
        sort(a, 0, N - 1, 0)
    }

    /**
     * 字符串排序
     *
     * @param a 排序的字符串数组
     * @param lo 排序的起始位置
     * @param hi 排序的结束位置
     * @param d 字符串排序的字符位置
     */
    private fun sort(a: Array<String>, lo: Int, hi: Int, d: Int) {
        if (hi <= lo + M) {
            insertion(a, lo, hi, d)
            return
        }

        val count = Array(R + 2, {0})

        for (i in lo .. hi) {
            count[charAt(a[i], d) + 2]++
        }

        for (r in 0 .. R) {
            count[r + 1] += count[r]
        }

        for (i in lo .. hi) {
            aux!![count[charAt(a[i], d) + 1]++] = a[i]
        }

        System.arraycopy(aux, 0, a, lo, hi + 1 - lo)

        for (r in 0 until R) {
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1)
        }
    }

    /**
     * 插入排序
     *
     * @param a 需要排序的数组
     * @param lo 起始位置
     * @param hi 结束位置
     * @param d 字符位置
     */
    private fun insertion(a: Array<String>, lo: Int, hi: Int, d: Int) {
        for (i in lo .. hi) {
            var j = i
            while (j > lo && less(a[j], a[j - 1], d)) {
                exch(a, j, j - 1)
                j--
            }
        }
    }

    /**
     * 交换两个字符串
     *
     * @param a 字符串数组
     * @param i 交换的一个字符串下标
     * @param j 交换的另一个字符串下标
     */
    private fun exch(a: Array<String>, i: Int, j: Int) {
        val temp = a[i]
        a[j] = a[j]
        a[j] = temp
    }

    /**
     * 比较两个字符串的大小
     *
     * @param v 一个字符串
     * @param w 另一个字符串
     * @param d 比较的起始字符位置
     * @return `true` v < w
     *         `false` v > w
     */
    private fun less(v: String, w: String, d: Int): Boolean {
        for (i in d until Math.min(v.length, w.length)) {
            if (v[i] < w[i]) return true
            if (v[i] > w[i]) return false
        }
        return v.length < w.length
    }
}