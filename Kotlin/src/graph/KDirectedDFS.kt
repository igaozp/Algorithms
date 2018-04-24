package graph

import edu.princeton.cs.algs4.Bag
import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In

/**
 * 有向图的可达性
 *
 * 检测顶点能够到达的其他顶点
 *
 * @author igaozp
 * @since 2017-09-09
 * @version 1.0
 */
class KDirectedDFS {
    // 有向图每个顶点的访问情况
    private var marked = MutableList(0, { false })
    // 可达顶点的数量
    private var count = 0

    /**
     * 构造方法
     */
    constructor(G: Digraph, s: Int) {
        marked = MutableList(G.V(), { false })
        validateVertex(s)
        dfs(G, s)
    }

    /**
     * 构造方法
     */
    constructor(G: Digraph, sources: Iterable<Int>) {
        marked = MutableList(G.V(), { false })
        validateVertices(sources)
        sources
                .filterNot { marked[it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        count++
        marked[v] = true
        G.adj(v)
                .filterNot { marked[it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 检查顶点的访问情况
     *
     * @param v 指定的顶点
     * @return `true` 访问过
     *         `false` 未访问过
     */
    fun marked(v: Int): Boolean {
        validateVertex(v)
        return marked[v]
    }

    /**
     * 获取可达顶点的数量
     *
     * @return 顶点的数量
     */
    fun count() = count

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

    /**
     * 验证顶点集合
     *
     * @param vertices 需要验证的顶点集合
     */
    private fun validateVertices(vertices: Iterable<Int>?) {
        if (vertices == null) {
            throw IllegalArgumentException("argument is null")
        }
        for (v in vertices) {
            if (v < 0 || v >= marked.size) {
                throw IllegalArgumentException("vertex $v is not between 0 and ${marked.size - 1}")
            }
        }
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val graph = Digraph(In(args[0]))
    val sources = Bag<Int>()

    (1 until args.size).forEach { i -> sources.add(args[i].toInt()) }

    val readchable = KDirectedDFS(graph, sources)

    (0 until graph.V())
            .filter { readchable.marked(it) }
            .forEach { print("$it ") }
    println()
}