package me.metatom.java.graph;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.*;

/**
 * 无环加权有向图的最短路径算法
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-19
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

        validateVertex(s);

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        Topological top = new Topological(G);
        if (!top.hasOrder()) {
            throw new IllegalArgumentException("Digraph is not acyclic.");
        }

        for (int v : top.order()) {
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    /**
     * 顶点的松弛
     *
     * @param e 有向图的边
     */
    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();

        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    /**
     * 到达指定顶点的路径长度
     *
     * @param v 指定的顶点
     * @return 路径长度
     */
    public double distTo(int v) {
        validateVertex(v);
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
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    /**
     * 验证顶点的合法性
     *
     * @param v 需要验证的顶点
     */
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 单元测试程序
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        AcyclicSP sp = new AcyclicSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                for (DirectedEdge e : sp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d      no path   ", s, v);
            }
        }
    }
}
