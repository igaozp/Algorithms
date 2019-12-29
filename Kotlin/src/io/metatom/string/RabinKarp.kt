package io.metatom.string

import java.math.BigInteger
import java.util.*

/**
 * Rabin-Karp 指纹字符串查找算法
 *
 * @author igaozp
 * @since 2017-09-16
 * @version 1.0
 */
class RabinKarp(pat: String) {
    /**
     * 用于匹配的模式字符串
     */
    private var pat: String? = pat
    /**
     * 模式字符串的散列值
     */
    private var patHash: Long? = null
    /**
     * 模式字符串的长度
     */
    private var M: Int = 0
    /**
     * 一个很大的素数
     */
    private var Q: Int = 0
    /**
     * 字母表的大小
     */
    private var R: Int = 256
    /**
     * R ^ (M - 1) % Q
     */
    private var RM: Long = 0

    /**
     * 构造方法
     */
    init {
        this.M = pat.length
        Q = longRandomPrim().toInt()
        RM = 1
        for (i in 1 until M) {
            RM = (R * RM) % Q
        }
        patHash = hash(pat, M)
    }

    /**
     * 生成一个长的随机素数
     *
     * @return 素数
     */
    private fun longRandomPrim(): Long = BigInteger.probablePrime(31, Random()).toLong()

    /**
     * 检查模式是否匹配
     *
     * @param i 下标
     * @return `true` 匹配
     *         `false` 不匹配
     */
    fun check(i: Int): Boolean = true

    /**
     * 生成哈希值
     *
     * @param key 键
     * @param M 用于生成哈希值的键的位数
     * @return 哈希值
     */
    private fun hash(key: String, M: Int): Long {
        var h = 0
        for (j in 0 until M) {
            h = (R * h + key[j].toInt()) % Q
        }
        return h.toLong()
    }

    /**
     * 字符串搜素
     *
     * @param txt 用作搜索的文本
     * @return 搜索到的字符串下标
     */
    private fun search(txt: String): Int {
        val N = txt.length
        var txtHash = hash(txt, M)
        if (patHash == txtHash && check(0)) return 0
        for (i in M until N) {
            txtHash = (txtHash + Q - RM * txt[i - M].toInt() % Q) % Q
            txtHash = (txtHash * R + txt[i].toInt() % Q)
            if (patHash == txtHash) {
                if (check(i - M + 1)) {
                    return i - M + 1
                }
            }
        }
        return N
    }
}