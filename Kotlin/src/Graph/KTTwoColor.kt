package Graph

import edu.princeton.cs.algs4.Graph

/**
 * 双色问题
 *
 * 双色问题：能够用两种颜色将图的所有顶点着色，
 * 使得任意一条边的两个端点的颜色都不相同
 *
 * @author igaozp
 * @since 2017-09-11
 * @version 1.0
 */
class KTTwoColor(G: Graph) {
    /**
     * 存储顶点的访问情况
     */
    private var marked: Array<Boolean>? = null
    /**
     * 存储顶点的颜色状态
     */
    private var color: Array<Boolean>? = null
    /**
     * 是否是二分图
     */
    private var isTwoColorable: Boolean = true

    /**
     * 构造方法
     */
    init {
        marked = Array(G.V())
        color = Array(G.V())

        (0 until G.V()).forEach { s ->
            if (!marked!![s]) {
                dfs(G, s)
            }
        }
    }

    /**
     * 生成数组的辅助方法
     */
    private fun <T> Array(size: Int): Array<T> = Array(size)

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 起始顶点
     */
    private fun dfs(G: Graph, v: Int) {
        marked!![v] = true

        for (w in G.adj(v)) {
            if (!marked!![w]) {
                color!![w] = !color!![v]
                dfs(G, w)
            } else if (color!![w] == color!![v]) {
                isTwoColorable = false
            }
        }
    }

    /**
     * 是否是二分图
     *
     * @return `true` 是二分图
     *         `false` 不是二分图
     */
    fun isBipartite(): Boolean = isTwoColorable
}