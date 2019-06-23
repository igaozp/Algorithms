package me.metatom.java.graph;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.*;

/**
 * 基于队列的 Bellman-Ford 算法
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-19
 */
public class BellmanFordSP {
    /**
     * 到某个顶点的路径长度
     */
    private double[] distTo;
    /**
     * 到某个顶点的最后一条边
     */
    private DirectedEdge[] edgeTo;
    /**
     * 该顶点是否在队列中
     */
    private boolean[] onQueue;
    /**
     * 正在被放松的顶点
     */
    private Queue<Integer> queue;
    /**
     * relax() 的调用次数
     */
    private int cost;
    /**
     * 是否有负权重环
     */
    private Iterable<DirectedEdge> cycle;

    /**
     * 构造方法
     *
     * @param G 加权有向图
     * @param s 起始顶点
     */
    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        queue = new Queue<>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }

        assert check(G, s);
    }

    /**
     * 顶点的松弛
     *
     * @param G 加权有向图
     * @param v 指定的顶点
     */
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (cost++ % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) {
                    return;
                }
            }
        }
    }

    /**
     * 到指定顶点的路径长度
     *
     * @param v 指定的顶点
     * @return 路径长度
     */
    public double distTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        return distTo[v];
    }

    /**
     * 检查到指定顶点是否有路径
     *
     * @param v 指定的顶点
     * @return {@code true} 有路径
     * {@code false} 没有路径
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * 获取到指定顶点的路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    /**
     * 查找负权重的环
     */
    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (DirectedEdge anEdgeTo : edgeTo) {
            if (anEdgeTo != null) {
                spt.addEdge(anEdgeTo);
            }
        }
        EdgeWeightedDirectedCycle cf = new EdgeWeightedDirectedCycle(spt);
        cycle = cf.cycle();
    }

    /**
     * 检查是否有负权重的环
     *
     * @return {@code true} 有负权重的环
     * {@code false} 没有负权重的环
     */
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /**
     * 获取负权重的环
     *
     * @return 负权重的环
     */
    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    /**
     * 检查有向图
     *
     * @param G 带权重的有向图
     * @param s 起始顶点
     * @return {@code true} 合法
     * {@code false} 不合法
     */
    private boolean check(EdgeWeightedDigraph G, int s) {
        if (hasNegativeCycle()) {
            double weight = 0.0;
            for (DirectedEdge e : negativeCycle()) {
                weight += e.weight();
            }
            if (weight >= 0.0) {
                System.err.println("error: weight of negative cycle = " + weight);
                return false;
            }
        } else {
            if (distTo[s] != 0.0 || edgeTo[s] != null) {
                System.err.println("distanceTo[s] and edgeTo[s] inconsistent");
                return false;
            }
            for (int v = 0; v < G.V(); v++) {
                if (v == s) {
                    continue;
                }
                if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                    System.err.println("distTo[] and edgeTo[] inconsistent");
                    return false;
                }
            }

            for (int v = 0; v < G.V(); v++) {
                for (DirectedEdge e : G.adj(v)) {
                    int w = e.to();
                    if (distTo[v] + e.weight() < distTo[w]) {
                        System.err.println("edge " + s + " not relaxed");
                        return false;
                    }
                }
            }

            for (int w = 0; w < G.V(); w++) {
                if (edgeTo[w] == null) {
                    continue;
                }
                DirectedEdge e = edgeTo[w];
                int v = e.from();
                if (w != e.to()) {
                    return false;
                }
                if (distTo[v] + e.weight() != distTo[w]) {
                    System.err.println("edge " + e + " on shortest path not tight");
                    return false;
                }
            }
        }

        StdOut.println("Satisfies optimality conditions");
        StdOut.println();
        return true;
    }

    /**
     * 验证节点合法性
     *
     * @param v 需要验证的节点
     */
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        BellmanFordSP sp = new BellmanFordSP(G, s);

        if (sp.hasNegativeCycle()) {
            for (DirectedEdge e : sp.negativeCycle()) {
                StdOut.println(e);
            }
        } else {
            for (int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                    for (DirectedEdge e : sp.pathTo(v)) {
                        StdOut.print(e + "   ");
                    }
                    StdOut.println();
                } else {
                    StdOut.printf("%d to %d       no path\n", s, v);
                }
            }
        }
    }
}
