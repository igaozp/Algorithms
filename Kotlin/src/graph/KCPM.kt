package graph

import edu.princeton.cs.algs4.*
import edu.princeton.cs.algs4.DirectedEdge
import edu.princeton.cs.algs4.EdgeWeightedDigraph

/**
 * 优先级限制下的并行任务调度问题的关键路径方法
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.1
 */
class KCPM

fun main(args: Array<String>) {
    // 任务的数量
    val n = StdIn.readInt()
    StdIn.readLine()
    val g = EdgeWeightedDigraph(2 * n + 2)

    val s = 2 * n
    val t = 2 * n + 1

    // 构建网络
    for (i in 0 until n) {
        val a = StdIn.readLine().split("\\s+")
        val duration = a[0].toDouble()

        g.addEdge(DirectedEdge(i, i + n, duration))
        g.addEdge(DirectedEdge(s, i, 0.0))
        g.addEdge(DirectedEdge(i + n, t, 0.0))

        (1 until a.size)
                .map { a[it].toInt() }
                .forEach { g.addEdge(DirectedEdge(i + n, it, 0.0)) }

        // 计算最长的路径
        val lp = AcyclicLP(g, s)
        println("Start times:")

        for (i in 0 until n) {
            println("$i: ${lp.distTo(i)}")
        }

        println("Finish time: ${lp.distTo(t)}")
    }
}