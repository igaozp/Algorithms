package Graph

import edu.princeton.cs.algs4.Graph
import edu.princeton.cs.algs4.Queue
import edu.princeton.cs.algs4.Stack

/**
 * 使用广度优先搜索查找图中的路径
 *
 * @author igaozp
 * @since 2017-09-07
 * @version 1.0
 */
class KTBreadthFirstPaths(G: Graph, private var s: Int) {
    /**
     * 标记图中顶点的访问情况
     */
    private var marked: MutableList<Boolean?>? = null
    /**
     * 一棵由父链接标识的树，从起点到一个顶点的已知路径上的最后一个顶点
     */
    private var edgeTo: MutableList<Int?>? = null

    /**
     * 构造函数
     */
    init {
        marked = MutableList(G.V(), { null })
        edgeTo = MutableList(G.V(), { null })
        bfs(G, s)
    }

    /**
     * 广度优先搜索
     *
     * @param G 搜索的图
     * @param s 开始顶点
     */
    private fun bfs(G: Graph, s: Int) {
        val queue = Queue<Int>()
        marked!![s] = true
        queue.enqueue(s)
        while (!queue.isEmpty) {
            val v = queue.dequeue()
            for (w in G.adj(v)) {
                if (!marked?.get(w)!!) {
                    edgeTo!![w] = v
                    marked!![w] = true
                    queue.enqueue(w)
                }
            }
        }
    }

    /**
     * 检查特定顶点是否有相应的路径
     *
     * @param v 指定的顶点
     * @return `true` 有路径
     *         `false` 没有路径
     */
    fun hasPathTo(v: Int): Boolean = marked?.get(v)!!

    /**
     * 到指定顶点的路径
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