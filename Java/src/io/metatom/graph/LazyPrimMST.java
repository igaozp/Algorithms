package io.metatom.graph;

import edu.princeton.cs.algs4.*;

/**
 * 最小生成树的 Prim 算法的延时实现
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-17
 */
public class LazyPrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;
    /**
     * 最小生成树的顶点
     */
    private boolean[] marked;
    /**
     * 最小生成树的边
     */
    private Queue<Edge> mst;
    /**
     * 横切边
     */
    private MinPQ<Edge> pq;
    /**
     * 生成树的权重
     */
    private double weight;

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    public LazyPrimMST(io.metatom.graph.EdgeWeightedGraph G) {
        pq = new MinPQ<>();
        mst = new Queue<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                prim(G, v);
            }
        }

        assert check(G);
    }

    /**
     * Prim 算法
     *
     * @param G 带权重的无向图
     * @param s 起点
     */
    private void prim(io.metatom.graph.EdgeWeightedGraph G, int s) {
        scan(G, s);
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            assert marked[v] || marked[w];
            if (marked[v] && marked[w]) {
                continue;
            }
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) {
                scan(G, v);
            }
            if (!marked[w]) {
                scan(G, w);
            }
        }
    }

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param v 指定的顶点
     */
    private void scan(io.metatom.graph.EdgeWeightedGraph G, int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)]) {
                pq.insert(e);
            }
        }
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
     * @return 最小生成树的权重
     */
    public double weight() {
        return weight;
    }

    /**
     * 检查加权无向图
     *
     * @param G 无向图
     * @return 检查结果
     */
    private boolean check(io.metatom.graph.EdgeWeightedGraph G) {
        // check weight
        double totalWeight = 0.0;
        for (Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
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
        io.metatom.graph.EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
