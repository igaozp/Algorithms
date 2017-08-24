package Graph;

import edu.princeton.cs.algs4.Bag;

/**
 * 有向图
 *
 * @author igaozp
 * @since 2017-07-14
 * @version 1.0
 */
public class Digraph {
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
    private Bag<Integer>[] adj;

    /**
     * 有向图的构造方法
     *
     * @param V 初始化的顶点数量
     */
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /**
     * 获取有向图的顶点数量
     *
     * @return 顶点数量
     */
    public int V() {
        return V;
    }

    /**
     * 有向图的边的数量
     *
     * @return 边的数量
     */
    public int E() {
        return E;
    }

    /**
     * 添加一条边
     *
     * @param v 边的起始顶点
     * @param w 边的结束顶点
     */
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    /**
     * 获取以指定顶点为起始的所有顶点
     *
     * @param v 指定的顶点
     * @return 顶点的集合
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    /**
     * 有向图的反转
     *
     * @return 反转后的有向图
     */
    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                R.addEdge(w, v);
            }
        }
        return R;
    }
}
