package io.metatom.graph;

import edu.princeton.cs.algs4.*;

/**
 * 最小生成树的 Prim 算法
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-17
 */
public class PrimMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;
    /**
     * 距离树最近的边
     */
    private io.metatom.graph.Edge[] edgeTo;
    /**
     * 存储边的权重
     */
    private double[] distTo;
    /**
     * 顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 有效的横切边
     */
    private IndexMinPQ<Double> pq;

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    private PrimMST(io.metatom.graph.EdgeWeightedGraph G) {
        edgeTo = new io.metatom.graph.Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        pq = new IndexMinPQ<>(G.V());

        distTo[0] = 0.0;
        pq.insert(0, 0.0);
        while (!pq.isEmpty()) {
            visit(G, pq.delMin());
        }

        assert check(G);
    }

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param v 指定的顶点
     */
    private void visit(io.metatom.graph.EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (io.metatom.graph.Edge e : G.adj(v)) {
            int w = e.other(v);

            if (marked[w]) {
                continue;
            }
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();

                if (pq.contains(w)) {
                    pq.change(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    /**
     * 获取最小生成树
     *
     * @return 最小生成树
     */
    public Iterable<io.metatom.graph.Edge> edges() {
        Queue<io.metatom.graph.Edge> mst = new Queue<>();
        for (io.metatom.graph.Edge e : edgeTo) {
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    /**
     * 获取最小生成树的权重
     *
     * @return 最小生成树的权重
     */
    public double weight() {
        double weight = 0;
        for (io.metatom.graph.Edge e : this.edges()) {
            weight += e.weight();
        }
        return weight;
    }

    /**
     * 检查无向图
     *
     * @param G 需要检查的图
     * @return {@code true} 成功
     * {@code false} 失败
     */
    private boolean check(io.metatom.graph.EdgeWeightedGraph G) {
        // 检查图的权重
        double totalWeight = 0.0;
        for (io.metatom.graph.Edge e : edges()) {
            totalWeight += e.weight();
        }
        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
        }

        // 检查是否是无环图
        UF uf = new UF(G.V());
        for (io.metatom.graph.Edge e : edges()) {
            int v = e.either();
            int w = e.other(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
        }

        // 检查是否是生成树
        for (io.metatom.graph.Edge e : G.edges()) {
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // 检查是否是最小生成树
        for (io.metatom.graph.Edge e : edges()) {
            uf = new UF(G.V());
            for (io.metatom.graph.Edge f : edges()) {
                int x = f.either();
                int y = f.other(x);
                if (f != e) {
                    uf.union(x, y);
                }
            }

            for (io.metatom.graph.Edge f : G.edges()) {
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
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
