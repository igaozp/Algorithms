package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * 无向图
 *
 * 图由一组顶点和一组能够将两个顶点相连的边组成的
 * 使用邻接表实现无向图：一个以顶点为索引的列表数组，
 * 其中的每一个元素和该顶点相邻的顶点列表
 *
 * @author igaozp
 * @since 2017-07-11
 * @version 1.0
 */
public class Graph {
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
        this.V = V;
        this.E = E;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * 读取无向图并初始化
     *
     * @param in 数据输入流
     */
    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            adj[v] = new Bag<Integer>();
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
     * 向无向图中增加一条边
     *
     * @param v 边的起始顶点
     * @param w 边的结束顶点
     */
    public void addEdge(int v, int w) {
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

    public String toString() {
        StringBuilder s = new StringBuilder(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (int w : this.adj(v)) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
