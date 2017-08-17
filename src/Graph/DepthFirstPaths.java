package Graph;

import java.util.Stack;

/**
 * 使用深度优先搜索查找图中的路径
 *
 * @author igaozp
 * @since 2017-07-12
 * @version 1.0
 */
public class DepthFirstPaths {
    /**
     * 存储顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 一棵由父链接标识的树，从起点到一个顶点的已知路径上的最后一个顶点
     */
    private int[] edgeTo;
    /**
     * 起点
     */
    private final int s;

    /**
     * 构造方法
     *
     * @param G 搜索路径的图
     * @param s 路径的起点
     */
    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 搜索的起始顶点
     */
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * 检查指定顶点是否由相应的额路径
     *
     * @param v 指定的顶点
     * @return {@code true} 有路径
     *         {@code false} 没有路径
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * 到指定顶点的搜索路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }
}
