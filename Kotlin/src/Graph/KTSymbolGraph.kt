package Graph

import edu.princeton.cs.algs4.Graph
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.ST

/**
 * 符号图
 *
 * @author igaozp
 * @since 2017-09-11
 * @version 1.0
 */
class KTSymbolGraph(stream: String, sp: String) {
    /**
     * 符号表，用于索引
     */
    private var st: ST<String, Int>? = null
    /**
     * 反向索引
     */
    private var keys: MutableList<String?>? = null
    /**
     * 无向图
     */
    private var G: Graph? = null

    /**
     * 符号图的构造方法
     */
    init {
        st = ST()
        var input = In(stream)

        // 读取数据，并构造符号表
        while (input.hasNextLine()) {
            val a = input.readLine().split(sp)
            a.forEach { s ->
                if (!st!!.contains(s)) {
                    st!!.put(s, st!!.size())
                }
            }
        }

        // 构建反向索引
        keys = MutableList(st!!.size(), { null })
        for (name in st!!.keys()) {
            keys!![st!!.get(name)] = name
        }

        // 构建符号图
        G = Graph(st!!.size())
        input = In(stream)
        while (input.hasNextLine()) {
            val a = input.readLine().split(sp)
            val v = st!!.get(a[0])
            for (i in 1 until a.size) {
                G!!.addEdge(v, st!!.get(a[i]))
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
    fun contains(s: String): Boolean = st!!.contains(s)

    /**
     * 获取指定字符串的索引
     *
     * @param s 指定的字符串
     * @return 字符串索引
     */
    fun index(s: String): Int = if (contains(s)) {
        st!!.get(s)
    } else {
        -1
    }

    /**
     * 根据索引获取字符串
     *
     * @param v 指定的索引
     * @return 字符串
     */
    fun name(v: Int): String = keys?.get(v)!!

    /**
     * 获取符号图
     *
     * @return 符号图
     */
    fun G(): Graph? = G
}