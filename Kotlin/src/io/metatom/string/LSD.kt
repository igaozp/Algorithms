package io.metatom.string

/**
 * 低位优先的字符排序算法
 *
 * @author igaozp
 * @since 2017-09-13
 * @version 1.0
 */
class LSD {
    /**
     * 字符串排序
     *
     * 假设每一个字符串的长度相等，从每一个字符串的低位到高位进行排序，
     * 最后完成字符串的排序
     *
     * @param a 需要排序的字符串数组
     * @param W 字符串长度
     */
    fun sort(a: Array<String>, W: Int) {
        val N = a.size
        val R = 256
        val aux = Array(N) { "" }

        for (d in (W - 1) downTo 0) {
            // 计算出现的频率
            val count = Array(R + 1) { 0 }
            for (s in a) {
                count[s[d].toInt() + 1]++
            }

            // 将频率转换为索引
            for (r in 0 until R) {
                count[r + 1] += count[r]
            }

            // 将元素分类
            for (s in a) {
                aux[count[s[d].toInt() + 1]] = s
            }

            // 回写
            System.arraycopy(aux, 0, a, 0, N)
        }
    }
}