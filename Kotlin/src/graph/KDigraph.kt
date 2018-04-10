package graph

import edu.princeton.cs.algs4.Bag
import edu.princeton.cs.algs4.In
import edu.princeton.cs.algs4.Stack

/**
 * 有向图
 *
 * @author igaozp
 * @since 2017-09-09
 * @version 1.0
 */
class KDigraph {
    // 边的数量
    private var E = 0
    // 顶点的数量
    private var V = 0
    // 邻接表
    private var adj = MutableList(0, { Bag<Int>() })
    // 存放节点的入度
    private var indegree = MutableList(0, { 0 })

    /**
     * 构造方法
     */
    constructor(V: Int) {
        if (V < 0) {
            throw IllegalArgumentException("Number of vertices in a Digraph must be nonnegative")
        }
        this.V = V
        this.E = 0
        adj = MutableList(V, { Bag<Int>() })
        for (v in 0 until V) {
            adj[v] = Bag()
        }
    }

    /**
     * 构造方法
     */
    constructor(input: In) {
        try {
            this.V = input.readInt()
            if (V < 0) {
                throw IllegalArgumentException("number of vertices in Digraph must be nonnegative")
            }
            this.indegree = MutableList(V, { 0 })
            adj = MutableList(V, { Bag<Int>() })
            for (v in 0 until V) {
                adj[v] = Bag()
            }
            val E = input.readInt()
            if (E < 0) {
                throw IllegalArgumentException("number of edges in a Digraph must be nonnegative")
            }
            for (i in 0 until E) {
                val v = input.readInt()
                val w = input.readInt()
                addEdge(v, w)
            }
        } catch (e: NoSuchElementException) {
            throw IllegalArgumentException("invalid input format Digraph constructor $e")
        }
    }

    /**
     * 构造方法
     */
    constructor(G: Digraph) : this(G.V()) {
        this.E = G.E()
        for (v in 0 until V) {
            this.indegree[v] = G.indegree(v)
        }
        for (v in 0 until G.V()) {
            val reverse = Stack<Int>()
            for (w in G.adj(v)) {
                reverse.push(w)
            }
            for (w in reverse) {
                adj[v].add(w)
            }
        }
    }

    /**
     * 获取有向图的顶点数量
     *
     * @return 顶点数量
     */
    fun V(): Int = V

    /**
     * 有向图的边的数量
     *
     * @return 边的数量
     */
    fun E(): Int = E

    /**
     * 添加一条边
     *
     * @param v 边的起始顶点
     * @param w 边的结束顶点
     */
    fun addEdge(v: Int, w: Int) {
        validateVertex(v)
        validateVertex(w)
        adj[v].add(w)
        E++
    }

    /**
     * 获取以指定顶点为起始的所有顶点
     *
     * @param v 指定的顶点
     * @return 顶点的集合
     */
    fun adj(v: Int): Iterable<Int> {
        validateVertex(v)
        return adj[v]
    }

    /**
     * 节点的出度
     *
     * @param v 指定的节点
     * @return 节点的出度
     */
    fun outdegree(v: Int): Int {
        validateVertex(v)
        return adj[v].size()
    }

    /**
     * 节点的入度
     *
     * @param v 指定的节点
     * @return 指定节点的入度
     */
    fun indegree(v: Int): Int {
        validateVertex(v)
        return indegree[v]
    }

    /**
     * 有向图的反转
     *
     * @return 反转后的有向图
     */
    fun reverse(): Digraph {
        val R = Digraph(V)
        for (v in 0 until V) {
            for (w in adj(v)) {
                R.addEdge(w, v)
            }
        }
        return R
    }

    /**
     * 验证节点
     *
     * @param v 需要验证的节点
     */
    private fun validateVertex(v: Int) {
        if (v < 0 || v >= V) {
            throw IllegalArgumentException("vertex $v is not between 0 and ${V - 1}")
        }
    }

    override fun toString(): String {
        var s = "$V + vertices, $E edges \n"
        for (v in 0 until V) {
            s += "$v: "
            for (w in adj[v]) {
                s += "$w "
            }
            s += "\n"
        }
        return s
    }
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val input = In(args[0])
    val graph = Digraph(input)
    println(graph)
}