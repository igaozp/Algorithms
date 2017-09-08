package Graph

import Base.Stack

/**
 * 使用深度优先搜索查找图中的路径
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class KTDepthFirstPaths(G: Graph, s: Int) {
    /**
     * 存储顶点的访问情况
     */
    private var marked: Array<Boolean>? = null
    /**
     * 一棵由父链接标识的树，从起点到一个顶点的已知路径上的最后一个顶点
     */
    private var edgeTo: Array<Int>? = null
    /**
     * 起点
     */
    private var s: Int? = s

    /**
     * 构造方法
     */
    init {
        marked = Array(G.V())
        edgeTo = Array(G.V())
        dfs(G, s)
    }

    /**
     * 初始化数组用的辅助函数
     */
    private fun <T> Array(size: Int): Array<T> = Array(size)

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 搜索的起始顶点
     */
    fun dfs(G: Graph, v: Int) {
        marked!![v] = true
        for (w in G.adj(v)) {
            if (!marked!![w]) {
                edgeTo!![w] = v
                dfs(G, w)
            }
        }
    }

    /**
     * 检查指定顶点是否由相应的路径
     *
     * @param v 指定的顶点
     * @return `true` 有路径
     *         `false` 没有路径
     */
    fun hasPathTo(v: Int): Boolean = marked!![v]

    /**
     * 到指定顶点的搜索路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    fun pathTo(v: Int): Iterable<Int>? {
        if (!hasPathTo(v)) return null
        val path = Stack<Int>()
        var x = v
        while (x != s) {
            path.push(x)
            x = edgeTo!![x]
        }
        path.push(s)
        return path
    }
}