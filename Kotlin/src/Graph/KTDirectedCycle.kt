package Graph

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.Stack

/**
 * 检测有向图的环
 *
 * @author igaozp
 * @since 2017-07-14
 * @version 1.0
 */
class KTDirectedCycle(G: Digraph) {
    /**
     * 图中每个节点的访问情况
     */
    private var marked: MutableList<Boolean?>? = null
    /**
     * 存储边的信息
     */
    private var edgeTo: MutableList<Int?>? = null
    /**
     * 存储环中的顶点
     */
    private var cycle: Stack<Int>? = null
    /**
     * 存储递归调用的栈上的所有顶点
     */
    private var onStack: MutableList<Boolean?>? = null

    /**
     * 构造方法
     */
    init {
        onStack = MutableList(G.V(), { null })
        edgeTo = MutableList(G.V(), { null })
        marked = MutableList(G.V(), { null })
        (0 until G.V())
                .filterNot { marked?.get(it)!! }
                .forEach { dfs(G, it) }
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的有向图
     * @param v 起始顶点
     */
    private fun dfs(G: Digraph, v: Int) {
        onStack!![v] = true
        marked!![v] = true

        for (w in G.adj(v)) {
            if (this.hasCycle()) {
                return
            } else if (!marked?.get(w)!!) {
                edgeTo!![w] = v
                dfs(G, w)
            } else if (onStack?.get(w)!!) {
                cycle = Stack()
                var x = v
                while (x != w) {
                    cycle!!.push(x)
                    x = edgeTo?.get(x)!!
                }
                cycle!!.push(w)
                cycle!!.push(v)
            }
        }
        onStack!![v] = false
    }

    /**
     * 检测是否有环
     *
     * @return `true` 有环
     *         `false` 无环
     */
    fun hasCycle(): Boolean = cycle != null

    /**
     * 获取有向图的环
     *
     * @return 有向图的环
     */
    fun cycle(): Iterable<Int> = cycle!!
}