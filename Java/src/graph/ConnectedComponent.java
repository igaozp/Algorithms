package graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Graph;

/**
 * 使用深度优先搜索查找图中的连通分量
 *
 * @author igaozp
 * @since 2017-07-13
 * @version 1.0
 */
public class ConnectedComponent {
    /**
     * 存储图中顶点的访问状态
     */
    private boolean[] marked;
    /**
     * 连通分量的标识符
     */
    private int[] id;
    /**
     * 存放给定分量的顶点数量
     */
    private int[] size;
    /**
     * 顶点的访问次数
     */
    private int count;

    /**
     * 构造方法
     *
     * @param G 初始化的图
     */
    public ConnectedComponent(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];

        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    /**
     * 构造方法
     *
     * @param G 带权重的图
     */
    public ConnectedComponent(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 起始顶点
     */
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;

        // 访问与 v 连通的顶点
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 起始顶点
     */
    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;

        // 访问与 v 连通的顶点
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (!marked[w]) dfs(G, w);
        }
    }

    /**
     * 检查两个连通分量
     *
     * @param v 连通分量
     * @param w 连通分量
     * @return {@code true} 两个分量连通
     *         {@code false} 两个分量不连通
     */
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    /**
     * 检查某个顶点的连通分量的标识符
     *
     * @param v 指定的顶点
     * @return 标识符
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    /**
     * 图的访问次数
     *
     * @return 访问次数
     */
    public int count() {
        return count;
    }

    /**
     * 获取指定顶点的联通顶点数量
     *
     * @param v  指定的顶点
     * @return 顶点的数量
     */
    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }

    /**
     * 验证顶点
     *
     * @param v 验证的顶点
     */
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        ConnectedComponent cc = new ConnectedComponent(G);

        int m = cc.count();
        StdOut.println(m + " components");

        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }

        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
