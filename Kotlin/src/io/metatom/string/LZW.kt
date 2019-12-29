package io.metatom.string

import edu.princeton.cs.algs4.BinaryStdIn
import edu.princeton.cs.algs4.BinaryStdOut
import edu.princeton.cs.algs4.TST

/**
 * LZW 压缩
 *
 * @author igaozp
 * @since 2017-09-14
 * @version 1.0
 */
class LZW {
    /**
     * 输入的字符数
     */
    private var R = 256
    /**
     * 编码总数
     */
    private var L = 4096
    /**
     * 编码宽度
     */
    private var  W = 12

    /**
     * 压缩
     */
    fun compress() {
        var input = BinaryStdIn.readString()
        val st = TST<Int>()

        for (i in 0 until R) {
            st.put("${i.toChar()}", i)
        }

        var code = R + 1

        while (input.isNotEmpty()) {
            val s = st.longestPrefixOf(input)
            BinaryStdOut.write(st.get(s), W)

            val t = s.length
            if (t < input.length && code < L) {
                st.put(input.substring(0, t + 1), code++)
            }
            input = input.substring(t)
        }

        BinaryStdOut.write(R, W)
        BinaryStdOut.close()
    }

    /**
     * 解码
     */
    fun expand() {
        val st = arrayOfNulls<String>(L)
        var i = 0
        while (i < R) {
            st[i] = "" + i.toChar()
            i++
        }
        st[i++] = " "

        var codeword = BinaryStdIn.readInt()
        var value: String? = st[codeword]

        while (true) {
            BinaryStdOut.write(value)
            codeword = BinaryStdIn.readInt(W)
            if (codeword == R) {
                break
            }

            var s = st[codeword]
            if (i == codeword) {
                s = value + value?.get(0)
            }

            if (i < L) {
                st[i++] = value + s?.get(0)
            }
            value = s
        }
        BinaryStdOut.close()
    }
}
