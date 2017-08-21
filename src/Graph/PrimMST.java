package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * 最小生成树的 Prim 算法
 *
 * @author igaozp
 * @since 2017-07-17
 * @version 1.0
 */
public class PrimMST {
    /**
     * 距离树最近的边
     */
    private Edge[] edgeTo;
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
     * @param G 加权有向图
     */
    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
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
    }

    /**
     * 访问加权有向图的顶点
     *
     * @param G 加权有向图
     * @param v 指定的顶点
     */
    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
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
    public Iterable<Edge> edges() {
        Bag<Edge> mst = new Bag<Edge>();
        for (int v = 1; v < edgeTo.length; v++) {
            mst.add(edgeTo[v]);
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
        for (Edge e : this.edges()) {
            weight += e.weight();
        }
        return weight;
    }
}
