package Graph;

import java.util.Stack;

/**
 * 检测有向图的环
 *
 * @author igaozp
 * @since 2017-07-14
 * @version 1.0
 */
public class DirectedCycle {
    /**
     * 图中每个节点的访问情况
     */
    private boolean[] marked;
    /**
     * 存储边的信息
     */
    private int[] edgeTo;
    /**
     * 存储环中的顶点
     */
    private Stack<Integer> cycle;
    /**
     * 存储递归调用的栈上的所有顶点
     */
    private boolean[] onStack;

    /**
     * 构造方法
     *
     * @param G 有向图
     */
    public DirectedCycle(Digraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的有向图
     * @param v 起始顶点
     */
    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (this.hasCycle()) {
                return;
            } else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    /**
     * 检测是否有环
     *
     * @return {@code true} 有环
     *         {@code false} 无环
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * 获取有向图的环
     *
     * @return 有向图的环
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }
}
