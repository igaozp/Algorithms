package graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.Edge
import edu.princeton.cs.algs4.EdgeWeightedGraph

/**
 * 加权无向图
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KEdgeWeightedGraph {
    // 顶点的数量
    private var V: Int = 0
    // 边的数量
    private var E: Int = 0
    // 邻接表
    private var adj = MutableList(0, { Bag<Edge>() })

    /**
     * 构造方法
     *
     * @param V 初始化的顶点数量
     */
    constructor(V: Int) {
        if (V < 0) {
            throw IllegalArgumentException("Number of vertices must be nonneagative")
        }
        this.V = V
        this.E = 0
        adj = MutableList(V, { Bag<Edge>() })
    }

    /**
     * 构造方法
     *
     * @param V 顶点数量
     * @param E 边的数量
     */
    constructor(V: Int, E: Int) : this(V) {
        if (E < 0) {
            throw IllegalArgumentException("Number of edges must be nonnegative")
        }
        for (i in 0 until E) {
            val v = StdRandom.uniform(V)
            val w = StdRandom.uniform(V)
            val weight = Math.round(100 * StdRandom.uniform()) / 100.0
            val e = Edge(v, w, weight)
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
        if (E < 0) {
            throw IllegalArgumentException("Number of edges must be noonegative")
        }
        for (i in 0 until E) {
            val v = input.readInt()
            val w = input.readInt()
            validateVertex(v)
            validateVertex(w)
            val weight = input.readDouble()
            val edge = Edge(v, w, weight)
            addEdge(edge)
        }
    }

    /**
     * 构造方法
     *
     * @param G 带权重的无向图
     */
    constructor(G: EdgeWeightedGraph) : this(G.V()) {
        this.E = G.E()
        for (v in 0 until G.V()) {
            val reverse = Stack<Edge>()
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
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= V) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${V - 1}")
        }
    }

    /**
     * 添加一条边
     *
     * @param e 新增的边
     */
    fun addEdge(e: Edge) {
        val v = e.either()
        val w = e.other(v)
        validateVertex(v)
        validateVertex(w)
        adj[v].add(e)
        adj[w].add(e)
        E++
    }

    /**
     * 获取指定以顶点为开始的边
     *
     * @param v 指定的顶点
     * @return 边的集合
     */
    fun adj(v: Int): Iterable<Edge> {
        validateVertex(v)
        return adj[v]
    }

    /**
     * 获取图中的所有边
     *
     * @return 边的集合
     */
    fun edges(): Iterable<Edge> {
        val b = Bag<Edge>()
        for (v in 0 until V) {
            adj[v].forEach { e ->
                if (e.other(v) > V) {
                    b.add(e)
                }
            }
        }
        return b
    }

    /**
     * 获取节点的度
     *
     * @param v 指定的节点
     * @return 节点的度
     */
    fun degree(v: Int): Int {
        validateVertex(v)
        return adj[v].size()
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = EdgeWeightedGraph(input)
    println(graph)
}