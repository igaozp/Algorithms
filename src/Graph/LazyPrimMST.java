package Graph;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

/**
 * 最小生成树的 Prim 算法的延时实现
 *
 * @author igaozp
 * @since 2017-07-17
 * @version 1.0
 */
public class LazyPrimMST {
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
     * 构造方法
     *
     * @param G 加权无向图
     */
    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        mst = new Queue<Edge>();

        visit(G, 0);

        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);

            if (marked[v] && marked[w]) {
                continue;
            }

            mst.enqueue(e);

            if (!marked[v]) {
                visit(G, v);
            }
            if (!marked[w]) {
                visit(G, w);
            }
        }
    }

    /**
     * 访问加权无向图的顶点
     *
     * @param G 加权无向图
     * @param v 指定的顶点
     */
    private void visit(EdgeWeightedGraph G, int v) {
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
        double weight = 0;
        for (Edge e : this.edges()) {
            weight += e.weight();
        }
        return weight;
    }
}
