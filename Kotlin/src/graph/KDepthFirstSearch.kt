package graph

import edu.princeton.cs.algs4.In

/**
 * 深度优先搜索
 *
 * @author igaozp
 * @since 2017-07-12
 * @version 1.0
 */
class KDepthFirstSearch(G: Graph, s: Int) {
    // 存储图的每个顶点的访问情况
    private var marked = MutableList(0, { false })
    // 访问的次数
    private var count: Int = 0

    /**
     * 构造方法
     */
    init {
        marked = MutableList(G.V(), { false })
        validateVertex(s)
        dfs(G, s)
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜素的图
     * @param v 搜索的顶点
     */
    private fun dfs(G: Graph, v: Int) {
        marked[v] = true
        count++
        G.adj(v)
                .filterNot { marked[it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 检查顶点的访问情况
     *
     * @param w 顶点
     * @return `true` 访问过
     *         `false` 未访问过
     */
    fun marked(w: Int): Boolean {
        validateVertex(w)
        return marked[w]
    }

    /**
     * 获取顶点的访问次数
     *
     * @return 访问次数
     */
    fun count(): Int = count

    /**
     * 验证节点
     *
     * @param v 需要验证的节点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= marked.size) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${marked.size - 1}")
        }
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = Graph(input)
    val s = args[1].toInt()
    val search = KDepthFirstSearch(graph, s)

    for (v in 0 until graph.V()) {
        if (search.marked(v)) {
            print("$v")
        }
    }
    println()
    if (search.count() != graph.V()) {
        println("Not connected")
    } else {
        println("connected")
    }
}