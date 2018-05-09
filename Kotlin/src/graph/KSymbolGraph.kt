package graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.Graph
import edu.princeton.cs.algs4.SymbolGraph

/**
 * 符号图
 *
 * @author igaozp
 * @since 2017-09-11
 * @version 1.0
 */
class KSymbolGraph(stream: String, sp: String) {
    // 符号表，用于索引
    private var st = ST<String, Int>()
    // 反向索引
    private var keys = MutableList(0, { "" })
    // 无向图
    private var G: Graph = Graph(0)

    /**
     * 符号图的构造方法
     */
    init {
        var input = In(stream)

        // 读取数据，并构造符号表
        while (input.hasNextLine()) {
            val a = input.readLine().split(sp)
            a.forEach { s ->
                if (!st.contains(s)) {
                    st.put(s, st.size())
                }
            }
        }

        // 构建反向索引
        for (name in st.keys()) {
            keys[st.get(name)] = name
        }

        // 构建符号图
        G = Graph(st.size())
        input = In(stream)
        while (input.hasNextLine()) {
            val a = input.readLine().split(sp)
            val v = st.get(a[0])
            for (i in 1 until a.size) {
                G.addEdge(v, st.get(a[i]))
            }
        }
    }

    /**
     * 检查图中是否包含某个字符串
     *
     * @param s 指定的字符串
     * @return `true` 包含
     *         `false` 不包含
     */
    fun contains(s: String): Boolean = st.contains(s)

    /**
     * 获取指定字符串的索引
     *
     * @param s 指定的字符串
     * @return 字符串索引
     */
    fun index(s: String): Int = if (contains(s)) {
        st.get(s)
    } else {
        -1
    }

    /**
     * 根据索引获取字符串
     *
     * @param v 指定的索引
     * @return 字符串
     */
    fun name(v: Int): String {
        validateVertex(v)
        return keys[v]
    }

    /**
     * 获取符号图
     *
     * @return 符号图
     */
    fun G(): Graph = G

    /**
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= G.V()) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${G.V() - 1}")
        }
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val stream = args[0]
    val sp = args[1]
    val sg = SymbolGraph(stream, sp)
    val graph = sg.G()
    while (StdIn.hasNextLine()) {
        val source = StdIn.readLine()
        if (sg.contains(source)) {
            val s = sg.index(source)
            for (v in graph.adj(s)) {
                println("    ${sg.name(v)}")
            }
        } else {
            println("input not contain '$source'")
        }
    }
}