package Graph

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
class KTCPM {
    fun main(args: Array<String>) {
        val N = StdIn.readInt()
        StdIn.readLine()
        val G = EdgeWeightedDigraph(2 * N + 2)

        val s = 2 * N
        val t = 2 * N + 1

        for (i in 0 until N) {
            val a = StdIn.readLine().split("\\s+")
            val duration = a[0].toDouble()

            G.addEdge(DirectedEdge(i, i + N, duration))
            G.addEdge(DirectedEdge(s, i, 0.0))
            G.addEdge(DirectedEdge(i + N, t, 0.0))

            (1 until a.size)
                    .map { a[it].toInt() }
                    .forEach { G.addEdge(DirectedEdge(i + N, it, 0.0)) }

            val lp = AcyclicLP(G, s)
            StdOut.println("Start times:")

            for (i in 0 until N) {
                StdOut.printf("$i: ${lp.distTo(i)}")
            }

            StdOut.printf("Finish time: ${lp.distTo(t)}")
        }
    }
}