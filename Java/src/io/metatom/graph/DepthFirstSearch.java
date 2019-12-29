package io.metatom.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 深度优先搜索
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-12
 */
public class DepthFirstSearch {
    /**
     * 存储图的每个顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 访问的次数
     */
    private int count;

    /**
     * 构造函数
     *
     * @param G 用于初始化的图
     * @param s 搜索的开始顶点
     */
    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜素的图
     * @param v 搜索的顶点
     */
    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * 检查顶点的访问情况
     *
     * @param v 顶点
     * @return {@code true} 访问过
     * {@code false} 未访问过
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 获取顶点的访问次数
     *
     * @return 访问次数
     */
    public int count() {
        return count;
    }

    /**
     * 验证节点
     *
     * @param v 需要验证的节点
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
        int s = Integer.parseInt(args[1]);
        DepthFirstSearch search = new DepthFirstSearch(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
        if (search.count() != G.V()) {
            StdOut.println("NOT connected");
        } else {
            StdOut.println("connected");
        }
    }
}
