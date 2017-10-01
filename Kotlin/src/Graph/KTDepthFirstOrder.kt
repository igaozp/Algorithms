package Graph

import Base.Queue
import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.Stack

/**
 * 有向图中基于深度优先搜索的顶点排序
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class KTDepthFirstOrder(G: Digraph) {
    /**
     * 存储顶点的访问 状态
     */
    private var marked: MutableList<Boolean?>? = null
    /**
     * 顶点的先序排列
     */
    private var pre: Queue<Int>? = null
    /**
     * 顶点的后续排列
     */
    private var post: Queue<Int>? = null
    /**
     * 顶点的逆后序排列
     */
    private var reversePost: Stack<Int>? = null

    /**
     * 构造方法
     */
    init {
        pre = Queue()
        post = Queue()
        reversePost = Stack()
        marked = MutableList(G.V(), { null })

        (0 until G.V())
                .filterNot { marked?.get(it)!! }
                .forEach { dfs(G, it) }
    }

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        pre!!.enqueue(v)
        marked!![v] = true

        G.adj(v)
                .filterNot { marked?.get(it)!! }
                .forEach { dfs(G, it) }

        post!!.enqueue(v)
        reversePost!!.push(v)
    }

    /**
     * 获取顶点的先序排列
     *
     * @return 先序排列集合
     */
    fun pre(): Iterable<Int> = pre!!

    /**
     * 获取顶点的后序排列
     *
     * @return 后序排列集合
     */
    fun post(): Iterable<Int> = post!!

    /**
     * 获取顶点的逆后序排列
     *
     * @return 逆后序排列集合
     */
    fun reversePost(): Iterable<Int> = reversePost!!
}