package String

import edu.princeton.cs.algs4.BinaryStdIn
import edu.princeton.cs.algs4.BinaryStdOut
import edu.princeton.cs.algs4.TST

class KTLZW {
    private var R = 256

    private var L = 4096

    private var  W = 12

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
