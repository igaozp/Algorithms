package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * 加权无向图
 *
 * @author igaozp
 * @since 2017-07-16
 * @version 1.0
 */
public class EdgeWeightedGraph {
    /**
     * 顶点的数量
     */
    private final int V;
    /**
     * 边的数量
     */
    private int E;
    /**
     * 邻接表
     */
    private Bag<Edge>[] adj;

    /**
     * 构造方法
     *
     * @param V 初始化的顶点数量
     */
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

    /**
     * 构造方法
     *
     * @param in 输入流
     */
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();

        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            adj[v] = new Bag<Edge>();
        }
    }

    /**
     * 获取顶点的数量
     *
     * @return 顶点的数量
     */
    public int V() {
        return V;
    }

    /**
     * 获取边的数量
     *
     * @return 边的数量
     */
    public int E() {
        return E;
    }

    /**
     * 添加一条边
     *
     * @param e 新增的边
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);

        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    /**
     * 获取指定以顶点为开始的边
     *
     * @param v 指定的顶点
     * @return 边的集合
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    /**
     * 获取图中的所有边
     *
     * @return 边的集合
     */
    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                if (e.other(v) > V) {
                    b.add(e);
                }
            }
        }
        return b;
    }
}
