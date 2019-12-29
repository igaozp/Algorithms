package io.metatom.graph

import edu.princeton.cs.algs4.Graph
import edu.princeton.cs.algs4.Queue

/**
 * 图的广度优先搜索
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class BreadthFirstSearch(G: Graph, s: Int) {
    // 存储图顶点的访问情况
    private var marked: MutableList<Boolean> = MutableList(0) { false }
    // 存储顶点的访问次数
    private var count: Int = 0

    // 构造方法
    init {
        marked = MutableList(G.V()) { false }
        bfs(G, s)
    }

    // 广度优先搜索
    private fun bfs(G: Graph, s: Int) {
        // 将访问过的元素加入队列
        val queue = Queue<Int>()
        marked[s] = true
        queue.enqueue(s)

        // 依次访问以出队的元素为顶点的元素
        while (!queue.isEmpty) {
            val v = queue.dequeue()
            for (w in G.adj(v)) {
                // 没有访问则入队
                if (!marked[w]) {
                    marked[w] = true
                    queue.enqueue(w)
                }
            }
        }
    }

    // 检查指定的顶点是否访问过
    fun marked(w: Int): Boolean = marked[w]

    // 获取图的访问次数
    fun count(): Int = count
}