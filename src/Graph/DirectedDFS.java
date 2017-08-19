package Graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Digraph;

/**
 * 有向图的可达性
 *
 * 检测顶点能够到达的其他顶点
 *
 * @author igaozp
 * @since 2017-07-14
 * @version 1.0
 */
public class DirectedDFS {
    /**
     * 有向图每个顶点的访问情况
     */
    private boolean[] marked;

    /**
     * 构造方法
     *
     * @param G 有向图
     * @param s 起始顶点
     */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    /**
     * 构造方法
     *
     * @param G 有向图
     * @param sources 检测的顶点
     */
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
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
     *         {@code false} 未访问过
     */
    public boolean marked(int v) {
        return marked[v];
    }

    /**
     * 测试
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
