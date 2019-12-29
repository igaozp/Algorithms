package io.metatom.graph;

import edu.princeton.cs.algs4.*;

/**
 * 加权有向图
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-18
 */
public class EdgeWeightedDigraph {
    private static final String NEWLINE = System.getProperty("line.separator");
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
     * 存放顶点的入度
     */
    private int[] indegree;

    /**
     * 构造方法
     *
     * @param V 顶点的数量
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /**
     * 构造方法
     *
     * @param V 顶点的数量
     * @param E 边的数量
     */
    public EdgeWeightedDigraph(int V, int E) {
        this(V);
        if (E < 0) {
            throw new IllegalArgumentException("Number of edges in a Digraph must be nonnegative");
        }
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            double weight = 0.01 * StdRandom.uniform(100);
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
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
            adj[v] = new Bag<>();
        }
    }

    /**
     * 构造方法
     *
     * @param G 带权重的有向图
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            this.indegree[v] = G.indegree(v);
        }
        for (int v = 0; v < G.V(); v++) {
            Stack<DirectedEdge> reverse = new Stack<>();
            for (DirectedEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (DirectedEdge e : reverse) {
                adj[v].add(e);
            }
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
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not betweeb 0 and " + (V - 1));
        }
    }

    /**
     * 向有向图中添加一条边
     *
     * @param e 带权重的边
     */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        indegree[w]++;
        E++;
    }

    /**
     * 获取以指定顶点的边
     *
     * @param v 指定的顶点
     * @return 边的集合
     */
    public Iterable<DirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 顶点的出度
     *
     * @param v 指定的顶点
     * @return 顶点的出度
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 顶点的入度
     *
     * @param v 指定的顶点
     * @return 顶点的入度
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        StdOut.println();
    }
}
