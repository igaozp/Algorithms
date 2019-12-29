package io.metatom.graph;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.*;

/**
 * 优先级限制下的并行任务调度问题的关键路径方法
 *
 * @author igaozp
 * @version 1.1
 * @since 2017-07-19
 */
@SuppressWarnings("unused")
public class CPM {
    /**
     * 构造方法
     */
    private CPM() {
    }

    /**
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 任务的数量
        int n = StdIn.readInt();

        int source = 2 * n;
        int sink = 2 * n + 1;

        // 构建网络
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * n + 2);
        for (int i = 0; i < n; i++) {
            double duration = StdIn.readDouble();

            G.addEdge(new DirectedEdge(source, i, 0.0));
            G.addEdge(new DirectedEdge(i + n, sink, 0.0));
            G.addEdge(new DirectedEdge(i, i + n, duration));

            int m = StdIn.readInt();
            for (int j = 0; j < m; j++) {
                int precedent = StdIn.readInt();
                G.addEdge(new DirectedEdge(i + n, precedent, 0.0));
            }
        }

        // 计算最长的路径
        AcyclicLP lp = new AcyclicLP(G, source);

        StdOut.println("job start finish");
        StdOut.println("----------------");

        for (int i = 0; i < n; i++) {
            StdOut.printf("%4d %7.1f %7.1f\n", i, lp.distTo(i), lp.distTo(i + n));
        }

        StdOut.printf("Finish time: %7.1f\n", lp.distTo(sink));
    }
}
