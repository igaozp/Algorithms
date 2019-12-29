package io.metatom.graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 有向图的可达性
 * <p>
 * 检测顶点能够到达的其他顶点
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-14
 */
@SuppressWarnings("DuplicatedCode")
public class DirectedDFS {
    /**
     * 有向图每个顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 可达顶点的数量
     */
    private int count;

    /**
     * 构造方法
     *
     * @param G 有向图
     * @param s 起始顶点
     */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    /**
     * 构造方法
     *
     * @param G       有向图
     * @param sources 检测的顶点
     */
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        validateVertices(sources);
        for (int s : sources) {
            if (!marked[s]) {
                dfs(G, s);
            }
        }
    }

    /**
     * 深度优先搜索
     *
     * @param G 有向图
     * @param v 起始顶点
     */
    private void dfs(Digraph G, int v) {
        count++;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * 检查顶点的访问情况
     *
     * @param v 指定的顶点
     * @return {@code true} 访问过
     * {@code false} 未访问过
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 获取可达顶点的数量
     *
     * @return 顶点的数量
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
            throw new IllegalArgumentException("vertex " + v + " is not betwen 0 and " + (V - 1));
        }
    }

    /**
     * 验证顶点集合
     *
     * @param vertices 需要验证的顶点集合
     */
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
            }
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Digraph G = new Digraph(new In(args[0]));

        Bag<Integer> sources = new Bag<>();
        for (int i = 1; i < args.length; i++) {
            sources.add(Integer.parseInt(args[i]));
        }

        DirectedDFS readchable = new DirectedDFS(G, sources);

        for (int v = 0; v < G.V(); v++) {
            if (readchable.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
    }
}
