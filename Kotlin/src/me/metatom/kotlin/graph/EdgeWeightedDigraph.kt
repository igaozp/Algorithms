package me.metatom.kotlin.graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.DirectedEdge
import edu.princeton.cs.algs4.EdgeWeightedDigraph

/**
 * 加权有向图
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class EdgeWeightedDigraph {
    // 顶点数量
    private var V: Int = 0
    // 边的数量
    private var E: Int = 0
    // 存放顶点的入度
    private var indegree = MutableList(0) { 0 }
    // 邻接表
    private var adj = MutableList(0) { Bag<DirectedEdge>() }

    /**
     * 构造方法
     *
     * @param V 顶点的数量
     */
    constructor(V: Int) {
        if (V < 0) {
            throw IllegalArgumentException("Number of vertices in a Digraph must be nonnegative")
        }
        this.V = V
        this.E = 0
        adj = MutableList(V) { Bag<DirectedEdge>() }
    }

    /**
     * 构造方法
     *
     * @param V 顶点的数量
     * @param E 边的数量
     */
    constructor(V: Int, E: Int) : this(V) {
        if (E < 0) {
            throw IllegalArgumentException("Number of edges in a Digraph must be nonnegative")
        }
        for (i in 0 until E) {
            val v = StdRandom.uniform(V)
            val w = StdRandom.uniform(V)
            val weight = 0.01 * StdRandom.uniform(100)
            val e = DirectedEdge(v, w, weight)
            addEdge(e)
        }
    }

    /**
     * 构造方法
     *
     * @param input 输入流
     */
    constructor(input: In) : this(input.readInt()) {
        val E = input.readInt()
        for (i in 0 until E) {
            val v = input.readInt()
            var w = input.readInt()
            adj[v] = Bag()
        }
    }

    /**
     * 构造方法
     *
     * @param G 带权重的有向图
     */
    constructor(G: EdgeWeightedDigraph) : this(G.V()) {
        this.E = G.E()
        for (v in 0 until G.V()) {
            this.indegree[v] = G.indegree(v)
        }
        for (v in 0 until G.V()) {
            val reverse = Stack<DirectedEdge>()
            for (e in G.adj(v)) {
                reverse.push(e)
            }
            for (e in reverse) {
                adj[v].add(e)
            }
        }
    }

    /**
     * 获取顶点的数量
     *
     * @return 顶点的数量
     */
    fun V(): Int = V

    /**
     * 获取边的数量
     *
     * @return 边的数量
     */
    fun E(): Int = E

    /**
     * 获取以指定顶点的边
     *
     * @param v 指定的顶点
     * @return 边的集合
     */
    fun adj(v: Int): Iterable<DirectedEdge> {
        validateVertex(v)
        return adj[v]
    }

    /**
     * 顶点的出度
     *
     * @param v 指定的顶点
     * @return 顶点的出度
     */
    fun outdegree(v: Int): Int {
        validateVertex(v)
        return adj[v].size()
    }

    /**
     * 顶点的入度
     *
     * @param v 指定的顶点
     * @return 顶点的入度
     */
    fun indegree(v: Int): Int {
        validateVertex(v)
        return indegree[v]
    }

    /**
     * 获取有向图所有的边
     *
     * @return 边的集合
     */
    fun edges(): Iterable<DirectedEdge> {
        val bag = Bag<DirectedEdge>()
        (0 until V).forEach { v ->
            adj[v].forEach { e -> bag.add(e) }
        }
        return bag
    }

    /**
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= V) {
            throw IllegalArgumentException("vertex $v is not betweeb 0 and ${V - 1}")
        }
    }

    /**
     * 向有向图中添加一条边
     *
     * @param e 带权重的边
     */
    fun addEdge(e: DirectedEdge) {
        val v = e.from()
        val w = e.to()
        validateVertex(v)
        validateVertex(w)
        adj[v].add(e)
        indegree[w]++
        E++
    }
}

fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = EdgeWeightedDigraph(input)
    println(graph)
}