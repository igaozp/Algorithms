package Graph;

import Base.Queue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.UF;

/**
 * 最小生成树的 Kruskal 算法
 *
 * @author igaozp
 * @since 2017-07-17
 * @version 1.0
 */
public class KruskalMST {
    /**
     * 最小生成树
     */
    private Queue<Edge> mst;

    /**
     * 构造方法
     *
     * @param G 加权无向图
     */
    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        MinPQ<Edge> pq = new MinPQ<Edge>();
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
     * @return 最下生成树的权重
     */
    public double weight() {
        double weight = 0;
        for (Edge e : this.edges()) {
            weight += e.weight();
        }
        return weight;
    }
}
