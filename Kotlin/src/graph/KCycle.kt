package graph

import edu.princeton.cs.algs4.In
import java.util.*

/**
 * 检测无环图
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class KCycle {
    private var marked = MutableList(0, { false })
    private var edgeTo = MutableList(0, { 0 })
    private var cycle = Stack<Int>()

    // 构造方法
    constructor(G: Graph) {
        if (hasSelfLoop(G)) return
        if (hasParallelEdges(G)) return
        marked = MutableList(G.V(), { false })
        edgeTo = MutableList(G.V(), { 0 })
        (0 until G.V())
                .filterNot { marked[it] }
                .forEach { dfs(G, it, it) }
    }

    // 深度优先搜索
    private fun dfs(G: Graph, v: Int, u: Int) {
        marked[v] = true
        for (w in G.adj(v)) {
            if (cycle.size != 0) return

            if (!marked[w]) {
                edgeTo[w] = v
                dfs(G, w, v)
            } else if (w != u) {
                var x = v
                while (x != w) {
                    cycle.push(x)
                    x = edgeTo[x]
                }
                cycle.push(w)
                cycle.push(v)
            }
        }
    }

    // 检查图是否有环
    fun hasCycle(): Boolean = cycle.size > 0

    // 检查是否有指向自己的环
    private fun hasSelfLoop(G: Graph): Boolean {
        for (v in 0 until G.V()) {
            for (w in G.adj(v)) {
                if (v == w) {
                    cycle.push(v)
                    cycle.push(v)
                    return true
                }
            }
        }
        return false
    }

    // 检查是否有平行的边
    private fun hasParallelEdges(G: Graph): Boolean {
        marked = MutableList(G.V(), { false })

        for (v in 0 until G.V()) {
            for (w in G.adj(v)) {
                if (marked[w]) {
                    cycle.push(v)
                    cycle.push(w)
                    cycle.push(v)
                    return true
                }
                marked[w] = true
            }

            for (w in G.adj(v)) {
                marked[w] = false
            }
        }
        return false
    }

    // 返回图的环
    fun cycle(): Iterable<Int> = cycle
}

// 单元测试
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = Graph(input)
    val finder = KCycle(graph)

    if (finder.hasCycle()) {
        for (v in finder.cycle()) {
            println("$v ")
        }
        println()
    } else {
        println("Graph is acyclic")
    }
}