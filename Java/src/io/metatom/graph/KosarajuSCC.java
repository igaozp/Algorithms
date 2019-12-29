package io.metatom.graph;

import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.*;

/**
 * 计算强连通分量的 Kosaraju 算法
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-15
 */
public class KosarajuSCC {
    /**
     * 存储顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 强连通分量的标识符
     */
    private int[] id;
    /**
     * 强连通分量的数量
     */
    private int count;

    /**
     * 构造方法
     *
     * @param G 有向图
     */
    public KosarajuSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());

        for (int s : order.reversePost()) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }

        assert check(G);
    }

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 搜索的起始顶点
     */
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * 检测连个顶点是否是强连通分量
     *
     * @param v 有向图的顶点
     * @param w 有向图的顶点
     * @return {@code true} 是强连通分量
     * {@code false} 不是强连通分量
     */
    public boolean stronglyConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    /**
     * 获取指定顶点的标识符
     *
     * @param v 指定的顶点
     * @return 标识符
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    /**
     * 获取强连通分量的数量
     *
     * @return 强连通分量的数量
     */
    public int count() {
        return count;
    }

    /**
     * 检查有向图
     *
     * @param G 需要检查的有向图
     * @return 检查结果
     */
    private boolean check(Digraph G) {
        TransitiveClosure tc = new TransitiveClosure(G);
        for (int v = 0; v < G.V(); v++) {
            for (int w = 0; w < G.V(); w++) {
                if (stronglyConnected(v, w) != (tc.reachable(v, w)) && tc.reachable(w, v)) {
                    return false;
                }
            }
        }
        return true;
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
        Digraph G = new Digraph(in);
        KosarajuSCC scc = new KosarajuSCC(G);

        int m = scc.count();
        StdOut.println(m + " strong components");

        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<>();
        }
        for (int v = 0; v < G.V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
