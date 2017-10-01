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
    private var marked: MutableList<Boolean?>? = null
    /**
     * 一棵由父链接标识的树，从起点到一个顶点的已知路径上的最后一个顶点
     */
    private var edgeTo: MutableList<Int?>? = null
    /**
     * 起点
     */
    private var s: Int? = s

    /**
     * 构造方法
     */
    init {
        marked = MutableList(G.V(), { null })
        edgeTo = MutableList(G.V(), { null })
        dfs(G, s)
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 搜索的起始顶点
     */
    fun dfs(G: Graph, v: Int) {
        marked!![v] = true
        for (w in G.adj(v)) {
            if (!marked?.get(w)!!) {
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
    fun hasPathTo(v: Int): Boolean = marked?.get(v)!!

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
            x = edgeTo?.get(x)!!
        }
        path.push(s)
        return path
    }
}