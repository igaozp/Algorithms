package Graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

/**
 * 基于队列的 Bellman-Ford 算法
 *
 * @author igaozp
 * @since 2017-07-19
 * @version 1.0
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
    private boolean[] onQ;
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
        onQ = new boolean[G.V()];
        queue = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        queue.enqueue(s);
        onQ[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQ[v] = false;
            relax(G, v);
        }
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
                if (!onQ[w]) {
                    queue.enqueue(w);
                    onQ[w] = true;
                }
            }
            if (cost++ % G.V() == 0) {
                findNegativeCycle();
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
        return distTo[v];
    }

    /**
     * 检查到指定顶点是否有路径
     *
     * @param v 指定的顶点
     * @return {@code true} 有路径
     *         {@code false} 没有路径
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * 获取到指定顶点的路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    public Iterable<DirectedEdge> pathTo(int v) {
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
        EdgeWeightedDigraph spt;
        spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            if (edgeTo[v] != null) {
                spt.addEdge(edgeTo[v]);
            }
        }
        EdgeWeightedDirectedCycle cf = new EdgeWeightedDirectedCycle(spt);
        cycle = cf.cycle();
    }

    /**
     * 检查是否有负权重的环
     *
     * @return {@code true} 有负权重的环
     *         {@code false} 没有负权重的环
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
}
