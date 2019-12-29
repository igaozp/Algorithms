package io.metatom.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;
import io.metatom.base.Queue;

/**
 * 最小生成树的 Kruskal 算法
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-17
 */
@SuppressWarnings("DuplicatedCode")
public class KruskalMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;
    /**
     * 最小生成树
     */
    private Queue<Edge> mst;
    /**
     * 最小生成树的权重
     */
    private double weight;

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<>();
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }

        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (uf.connected(v, w)) {
                continue;
            }
            uf.union(v, w);
            mst.enqueue(e);
            weight += e.weight();
        }

        assert check(G);
    }

    /**
     * 获取最小生成树
     *
     * @return 最小生成树
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * 获取最小生成树的权重
     *
     * @return 最下生成树的权重
     */
    public double weight() {
        return weight;
    }

    /**
     * 检查带权重的无向图
     *
     * @param G 无向图
     * @return 检查结果
     */
    private boolean check(EdgeWeightedGraph G) {
        // check total weight
        double total = 0.0;
        for (Edge e : edges()) {
            total += e.weight();
        }
        if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equals weight(): %f vs. %f\n", total, weight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(G.V());
        for (Edge e : edges()) {
            int v = e.either();
            int w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : G.edges()) {
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : edges()) {
            // all edges in MST except e
            uf = new UF(G.V());
            for (Edge f : mst) {
                int x = f.either();
                int y = f.other(x);
                if (f != e) {
                    uf.union(x, y);
                }
            }

            // check that e is min weight edge in crossing cut
            for (Edge f : G.edges()) {
                int x = f.either();
                int y = f.other(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        KruskalMST mst = new KruskalMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
