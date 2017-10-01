package Graph

import edu.princeton.cs.algs4.Bag

/**
 * 有向图
 *
 * @author igaozp
 * @since 2017-09-09
 * @version 1.0
 */
class KTDigraph(private var V: Int) {
    /**
     * 边的数量
     */
    private var E: Int = 0
    /**
     * 邻接表
     */
    private var adj: MutableList<Bag<Int>?>? = null

    /**
     * 构造方法
     */
    init {
        this.E = 0
        adj = MutableList(V, { null })
        for (v in 0 until V) {
            adj!![v] = Bag()
        }
    }

    /**
     * 获取有向图的顶点数量
     *
     * @return 顶点数量
     */
    fun V(): Int = V

    /**
     * 有向图的边的数量
     *
     * @return 边的数量
     */
    fun E(): Int = E

    /**
     * 添加一条边
     *
     * @param v 边的起始顶点
     * @param w 边的结束顶点
     */
    fun addEdge(v: Int, w: Int) {
        adj?.get(v)!!.add(w)
        E++
    }

    /**
     * 获取以指定顶点为起始的所有顶点
     *
     * @param v 指定的顶点
     * @return 顶点的集合
     */
    fun adj(v: Int): Iterable<Int> = adj?.get(v)!!

    /**
     * 有向图的反转
     *
     * @return 反转后的有向图
     */
    fun reverse(): Digraph {
        val R = Digraph(V)
        for (v in 0 until V) {
            for  (w in adj(v)) {
                R.addEdge(w, v)
            }
        }
        return R
    }
}