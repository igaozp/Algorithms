package Graph;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;

/**
 * 无环加权有向图的最短路径算法
 *
 * @author igaozp
 * @since 2017-07-19
 * @version 1.0
 */
public class AcyclicSP {
    /**
     * 路径的边
     */
    private DirectedEdge[] edgeTo;
    /**
     * 路径长度
     */
    private double[] distTo;

    /**
     * 构造方法
     *
     * @param G 加权有向图
     * @param s 起始顶点
     */
    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        Topological top = new Topological(G);
        for (int v : top.order()) {
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
            }
        }
    }

    /**
     * 到达指定顶点的路径长度
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
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
