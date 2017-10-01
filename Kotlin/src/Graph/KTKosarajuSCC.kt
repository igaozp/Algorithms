package Graph

import edu.princeton.cs.algs4.DepthFirstOrder
import edu.princeton.cs.algs4.Digraph

/**
 * 计算强连通分量的 Kosaraju 算法
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KTKosarajuSCC {
    /**
     * 存储顶点的访问情况
     */
    private var marked: MutableList<Boolean?>? = null
    /**
     * 强连通分量的标识符
     */
    private var id: MutableList<Int?>? = null
    /**
     * 强连通分量的数量
     */
    private var count: Int = 0

    /**
     * 构造方法
     *
     * @param G 有向图
     */
    constructor(G: Digraph) {
        marked = MutableList(G.V(), { null })
        id = MutableList(G.V(), { null })
        val order = DepthFirstOrder(G.reverse())

        for (s in order.reversePost()) {
            if (!marked?.get(s)!!) {
                dfs(G, s)
                count++
            }
        }
    }

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 搜索的起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        marked!![v] = true
        id!![v] = count

        G.adj(v).forEach { w ->
            if (!marked?.get(w)!!) {
                dfs(G, w)
            }
        }
    }

    /**
     * 检测连个顶点是否是强连通分量
     *
     * @param v 有向图的顶点
     * @param w 有向图的顶点
     * @return `true` 是强连通分量
     *         `false` 不是强连通分量
     */
    fun stronglyConnected(v: Int, w: Int): Boolean = id!![v] == id!![w]

    /**
     * 获取指定顶点的标识符
     *
     * @param v 指定的顶点
     * @return 标识符
     */
    fun id(v: Int): Int = id?.get(v)!!

    /**
     * 获取强连通分量的数量
     *
     * @return 强连通分量的数量
     */
    fun count(): Int = count
}