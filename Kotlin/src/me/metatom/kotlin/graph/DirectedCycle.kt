package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Stack

/**
 * 检测有向图的环
 *
 * @author igaozp
 * @since 2017-07-14
 * @version 1.0
 */
class DirectedCycle(G: Digraph) {
    // 图中每个节点的访问情况
    private var marked = MutableList(0) { false }
    // 存储边的信息
    private var edgeTo = MutableList(0) { 0 }
    // 存储环中的顶点
    private var cycle = Stack<Int>()
    // 存储递归调用的栈上的所有顶点
    private var onStack = MutableList(0) { false }

    /**
     * 构造方法
     */
    init {
        onStack = MutableList(G.V()) { false }
        edgeTo = MutableList(G.V()) { 0 }
        marked = MutableList(G.V()) { false }
        (0 until G.V())
                .filterNot { marked[it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的有向图
     * @param v 起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        onStack[v] = true
        marked[v] = true

        for (w in G.adj(v)) {
            if (this.hasCycle()) {
                return
            } else if (!marked[w]) {
                edgeTo[w] = v
                dfs(G, w)
            } else if (onStack[w]) {
                cycle = Stack()
                var x = v
                while (x != w) {
                    cycle.push(x)
                    x = edgeTo[x]
                }
                cycle.push(w)
                cycle.push(v)
            }
        }
        onStack[v] = false
    }

    /**
     * 检测是否有环
     *
     * @return `true` 有环
     *         `false` 无环
     */
    fun hasCycle(): Boolean = cycle.size() != 0

    /**
     * 获取有向图的环
     *
     * @return 有向图的环
     */
    fun cycle(): Iterable<Int> = cycle

    /**
     * 检查有向图的有向环
     *
     * @return {@code true} 通过验证
     *         {@code false} 未通过验证
     */
    private fun check(): Boolean {
        if (hasCycle()) {
            var first = -1
            var last = -1
            cycle().forEach {
                if (first == -1) {
                    first = it
                }
                last = it
            }
            if (first != last) {
                println("cycle begins with $first and ends with $last")
                return false
            }
        }
        return true
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = Digraph(input)
    val finder = DirectedCycle(graph)

    if (finder.hasCycle()) {
        print("Directed cycle: ")
        finder.cycle().forEach {
            print("$it ")
        }
        println()
    } else {
        println("No directed cycle")
    }
    println()
}