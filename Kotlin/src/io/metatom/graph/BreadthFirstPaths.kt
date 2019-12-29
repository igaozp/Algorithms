package io.metatom.graph

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
class BreadthFirstPaths(G: Graph, s: Int) {
    private val infinity = Int.MAX_VALUE
    // 标记图中顶点的访问情况
    private var marked: MutableList<Boolean?> = MutableList(0) { null }
    // 一棵由父链接标识的树，从起点到一个顶点的已知路径上的最后一个顶点
    private var edgeTo: MutableList<Int?> = MutableList(0) { null }
    // 存放路径的长度
    private var distTo: MutableList<Int?> = MutableList(0) { null }

    // 构造函数
    init {
        marked = MutableList(G.V()) { null }
        edgeTo = MutableList(G.V()) { null }
        distTo = MutableList(G.V()) { null }
        validateVertex(s)
        bfs(G, s)

        assert(check(G, s))
    }

    // 广度优先搜索
    private fun bfs(G: Graph, s: Int) {
        val queue = Queue<Int>()
        for (v in 0 until G.V()) {
            distTo[v] = infinity
        }
        distTo[s] = 0
        marked[s] = true
        queue.enqueue(s)

        while (!queue.isEmpty) {
            val v = queue.dequeue()
            for (w in G.adj(v)) {
                if (!marked[w]!!) {
                    edgeTo[w] = v
                    distTo[w] = distTo[v]!! + 1
                    marked[w] = true
                    queue.enqueue(w)
                }
            }
        }
    }

    // 检查特定顶点是否有相应的路径
    fun hasPathTo(v: Int): Boolean {
        validateVertex(v)
        return marked[v]!!
    }

    fun distTo(v: Int): Int {
        validateVertex(v)
        return distTo[v]!!
    }

    // 到指定顶点的路径
    fun pathTo(v: Int): Iterable<Int>? {
        if (!hasPathTo(v)) return null
        val path = Stack<Int>()
        var x = v
        while (distTo[x] != 0) {
            path.push(x)
            x = edgeTo[x]!!
        }
        path.push(x)
        return path
    }

    //合法性检测
    private fun check(G: Graph, s: Int): Boolean {
        if (distTo[s] != 0) {
            println("distance of success $s to itself = ${distTo[s]}")
            return false
        }

        for (v in 0 until G.V()) {
            for (w in G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    println("edge $v - $w")
                    println("hasPathTo($v) = ${hasPathTo(v)}")
                    println("hasPathTo($w) = ${hasPathTo(w)}")
                    return false
                }
                if (hasPathTo(v) && (distTo[w]!! > distTo[v]!! + 1)) {
                    println("edge $v - $w")
                    println("distTo[$v] = ${distTo[v]}")
                    println("distTo[$w] = ${distTo[w]}")
                    return false
                }
            }
        }

        for (w in 0 until G.V()) {
            if (!hasPathTo(w) || w == s) continue
            val v = edgeTo[w]
            if (distTo[w] != distTo[v!!]!! + 1) {
                println("shortest path edges $v - $w")
                println("distTo[$v] = ${distTo[v]}")
                println("distTo[$w] = ${distTo[w]}")
                return false
            }
        }
        return false
    }

    // 验证顶点
    private fun validateVertex(v: Int) {
        if (v < 0 || marked.size >= v) throw IllegalArgumentException("vertex $v is not between 0 and ${distTo.size - 1}")
    }

    // 验证顶点
    private fun validateVertices(vertices: Iterable<Int>?) {
        if (vertices == null) throw IllegalArgumentException("argument is null")
        for (v in vertices) {
            if (v < 0 || v >= marked.size) {
                throw IllegalArgumentException("vertex $v is not between 0 and ${marked.size - 1}")
            }
        }
    }
}