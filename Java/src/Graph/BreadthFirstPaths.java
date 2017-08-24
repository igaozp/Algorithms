package Graph;

import Base.Queue;

import java.util.Stack;

/**
 * 使用广度优先搜索查找图中的路径
 *
 * @author igaozp
 * @since 2017-07-12
 * @version 1.0
 */
public class BreadthFirstPaths {
    /**
     * 标记图中顶点的访问情况
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
     * @param G 初始化的图
     * @param s 开始顶点
     */
    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    /**
     * 广度优先搜索
     *
     * @param G 搜索的图
     * @param s 开始顶点
     */
    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    /**
     * 检查特定顶点是否有相应的路径
     *
     * @param v 指定的顶点
     * @return {@code true} 有路径
     *         {@code false} 没有路径
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * 到指定顶点的路径
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
