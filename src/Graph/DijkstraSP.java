package Graph;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

/**
 * 最短路径的 Dijkstra 算法
 *
 * @author igaozp
 * @since 2017-07-18
 * @version 1.0
 */
public class DijkstraSP {
    /**
     * 距离路径最近的边
     */
    private DirectedEdge[] edgeTo;
    /**
     * 边的权重
     */
    private double[] distTo;
    /**
     * 有效的横切边
     */
    private IndexMinPQ<Double> pq;

    /**
     * 构造方法
     *
     * @param G 加权有向图
     * @param s 路径的起点
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
            distTo[s] = 0.0;
            pq.insert(s, 0.0);

            while (!pq.isEmpty()) {
                relax(G, pq.delMin());
            }
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
                if (pq.contains(w)) {
                    pq.change(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    /**
     * 到指定顶点的距离
     *
     * @param v 指定的顶点
     * @return 距离
     */
    public double distTo(int v) {
        return distTo[v];
    }

    /**
     * 检查到指定的顶点是否由路径
     *
     * @param v 指定的顶点
     * @return {@code true} 有路径
     *         {@code false} 没有路径
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * 到指定顶点的路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
