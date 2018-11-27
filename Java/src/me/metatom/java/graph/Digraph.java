package me.metatom.java.graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 有向图
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-14
 */
public class Digraph {
    private static final String NEWLINE = System.getProperty("line.separator");
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
     * 存放节点的入度
     */
    private int[] indegree;

    /**
     * 有向图的构造方法
     *
     * @param V 初始化的顶点数量
     */
    public Digraph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /**
     * 构造方法
     *
     * @param in 输入流
     */
    public Digraph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("number of vertices in Digraph must be nonnegative");
            }
            indegree = new int[V];
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<>();
            }
            int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("number of edges in a Digraph must be nonnegative");
            }
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format Digraph constructor", e);
        }
    }

    /**
     * 构造方法
     *
     * @param G 有向图
     */
    public Digraph(Digraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < V; v++) {
            this.indegree[v] = G.indegree(v);
        }
        for (int v = 0; v < G.V(); v++) {
            Stack<Integer> reverse = new Stack<>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
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
     * 验证节点
     *
     * @param v 需要验证的节点
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 添加一条边
     *
     * @param v 边的起始顶点
     * @param w 边的结束顶点
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
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
        validateVertex(v);
        return adj[v];
    }

    /**
     * 节点的出度
     *
     * @param v 指定的节点
     * @return 节点的出度
     */
    public int outdegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 节点的入度
     *
     * @param v 指定的节点
     * @return 指定节点的入度
     */
    public int indegree(int v) {
        validateVertex(v);
        return indegree[v];
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

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
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
        Digraph G = new Digraph(in);
        StdOut.println(G);
    }
}
