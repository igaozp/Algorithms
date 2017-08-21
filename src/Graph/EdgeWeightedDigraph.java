package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * 加权有向图
 *
 * @author igaozp
 * @since 2017-07-18
 * @version 1.0
 */
public class EdgeWeightedDigraph {
    /**
     * 顶点数量
     */
    private final int V;
    /**
     * 边的数量
     */
    private int E;
    /**
     * 邻接表
     */
    private Bag<DirectedEdge>[] adj;

    /**
     * 构造方法
     *
     * @param V 顶点的数量
     */
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<DirectedEdge>();
        }
    }

    /**
     * 构造方法
     *
     * @param in 输入流
     */
    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            adj[v] = new Bag<DirectedEdge>();
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
     * 向有向图中添加一条边
     *
     * @param e 带权重的边
     */
    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    /**
     * 获取以指定顶点的边
     *
     * @param v 指定的顶点
     * @return 边的集合
     */
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    /**
     * 获取有向图所有的边
     *
     * @return 边的集合
     */
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj[v]) {
                bag.add(e);
            }
        }
        return bag;
    }
}
