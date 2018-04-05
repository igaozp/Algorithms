package graph

import base.Queue
import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Stack

/**
 * 有向图中基于深度优先搜索的顶点排序
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class KDepthFirstOrder {
    // 存储顶点的访问状态
    private var marked = MutableList(0, { false })
    // 顶点的先序排列
    private var preOrder = Queue<Int>()
    // 顶点的后续排列
    private var postOrder = Queue<Int>()
    // 存放先序序列相应节点的数字
    private var pre = MutableList(0, { 0 })
    // 存放后序序列相应节点的数字
    private var post = MutableList(0, { 0 })
    // 先序遍历序列编号的计数器
    private var preCounter = 0
    // 后序遍历序列编号的计数器
    private var postCounter = 0

    // 构造方法
    constructor(G: Digraph) {
        pre = MutableList(G.V(), { 0 })
        post = MutableList(G.V(), { 0 })
        marked = MutableList(G.V(), { false })

        (0 until G.V())
                .filterNot { marked[it] }
                .forEach { dfs(G, it) }
    }

    // 构造方法
    constructor(G: EdgeWeightedDigraph) {
        pre = MutableList(G.V(), { 0 })
        post = MutableList(G.V(), { 0 })
        marked = MutableList(G.V(), { false })

        for (v in 0 until G.V()) {
            if (!marked[v]) dfs(G, v)
        }
    }

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        preOrder.enqueue(v)
        marked[v] = true
        pre[v] = preCounter++

        G.adj(v)
                .filterNot { marked[it] }
                .forEach { dfs(G, it) }

        postOrder.enqueue(v)
        post[v] = postCounter++
    }

    /**
     * 深度优先搜索
     *
     * @param G 带权重的有向图
     * @param v 起始顶点
     */
    private fun dfs(G: EdgeWeightedDigraph, v: Int) {
        marked[v] = true
        pre[v] = preCounter++
        preOrder.enqueue(v)

        for (e in G.adj(v)) {
            val w = e.to()
            if (!marked[w]) dfs(G, w)
        }

        postOrder.enqueue(v)
        post[v] = postCounter++
    }

    /**
     * 获取后序遍历序列指定顶点的遍历次数
     *
     * @param v 指定的顶点
     * @return 后序遍历的次数
     */
    fun pre(v: Int): Int {
        validateVertex(v)
        return pre[v]
    }

    /**
     * 获取后序遍历序列指定顶点的遍历次数
     *
     * @param v 指定的顶点
     * @return 后序遍历的次数
     */
    fun post(v: Int): Int {
        validateVertex(v)
        return post[v]
    }

    /**
     * 获取顶点的先序排列
     *
     * @return 先序排列集合
     */
    fun pre(): Iterable<Int> = preOrder


    /**
     * 获取顶点的后序排列
     *
     * @return 后序排列集合
     */
    fun post(): Iterable<Int> = postOrder


    /**
     * 获取顶点的逆后序排列
     *
     * @return 逆后序排列集合
     */
    fun reversePost(): Iterable<Int> {
        val reverse = Stack<Int>()
        postOrder.forEach { reverse.push(it) }
        return reverse
    }

    /**
     * 检查相关函数和功能是否正常
     */
    private fun check(): Boolean {
        var r = 0
        post().forEach {
            if (post(it) != r) {
                println("post(v) and post() inconsistent")
                return false
            }
            r++
        }

        r = 0
        pre().forEach {
            if (pre(it) != r) {
                println("pre(v) and pre() inconsistent")
                return false
            }
            r++
        }

        return true
    }

    private fun validateVertex(v: Int) {
        val V = marked.size
        if (v < 0 || v >= V) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${V + 1}")
        }
    }
}

// 单元测试
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = Digraph(input)

    val dfs = KDepthFirstOrder(graph)
    println("    v pre post")
    println("--------------")
    for (v in 0 until graph.V()) {
        println("$v ${dfs.pre(v)} ${dfs.post(v)}")
    }

    println("PreOrder:  ")
    dfs.pre().forEach { println("$it ") }
    println()

    println("PostOrder:  ")
    dfs.post().forEach { println("$it ") }
    println()

    println("Reverse PostOrder:  ")
    dfs.reversePost().forEach { println("$it ") }
    println()
}