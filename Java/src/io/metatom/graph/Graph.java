package io.metatom.graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 无向图
 * <p>
 * 图由一组顶点和一组能够将两个顶点相连的边组成的
 * 使用邻接表实现无向图：一个以顶点为索引的列表数组，
 * 其中的每一个元素和该顶点相邻的顶点列表
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-11
 */
@SuppressWarnings("unused")
public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    /**
     * 无向图的顶点数量
     */
    private final int V;
    /**
     * 无向图的边的数量
     */
    private int E;
    /**
     * 邻接表
     */
    private Bag<Integer>[] adj;

    /**
     * 构造函数
     *
     * @param V 初始化的顶点数量
     */
    public Graph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = E;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /**
     * 读取无向图并初始化
     *
     * @param in 数据输入流
     */
    public Graph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            }
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<>();
            }
            int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            }
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    /**
     * 构造方法
     *
     * @param G 无向图
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
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
     * 获取无向图的顶点数量
     *
     * @return 顶点数量
     */
    public int V() {
        return V;
    }

    /**
     * 获取无向图边的数量
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
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 向无向图中增加一条边
     *
     * @param v 边的起始顶点
     * @param w 边的结束顶点
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    /**
     * 获取与指定顶点相连的顶点集合
     *
     * @param v 指定的顶点
     * @return 包含相连顶点的集合
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 在指定的图中获取某个顶点的度数
     *
     * @param G 指定的图
     * @param v 指定的顶点
     * @return 该顶点的度数
     */
    public static int degree(Graph G, int v) {
        int degree = 0;
        for (int w : G.adj(v)) {
            degree++;
        }
        return degree;
    }

    /**
     * 获取节点的度
     *
     * @param v 指定的节点
     * @return 节点的度
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 获取图中最大的度数
     *
     * @param G 指定的图
     * @return 最大的度数
     */
    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (degree(G, v) > max) {
                max = degree(G, v);
            }
        }
        return max;
    }

    /**
     * 获取图的平均度数
     *
     * @param G 指定的图
     * @return 平均度数
     */
    public static double avgDegree(Graph G) {
        return 2.0 * G.E() / G.V();
    }

    /**
     * 获取图的自环数量
     *
     * @param G 指定的图
     * @return 自环的数量
     */
    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    count++;
                }
            }
        }
        return count / 2;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (int w : this.adj(v)) {
                s.append(w).append(" ");
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
        Graph G = new Graph(in);
        StdOut.println(G);
    }
}
