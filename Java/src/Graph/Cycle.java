package Graph;

/**
 * 检测无环图
 *
 * @author igaozp
 * @since 2017-07-13
 * @version 1.0
 */
public class Cycle {
    /**
     * 存储每个顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 是否有环
     */
    private boolean hasCycle;

    /**
     * 构造函数
     *
     * @param G 初始化的图
     */
    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s, s);
            }
        }
    }

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 搜索的起点
     * @param u 对比顶点
     */
    private void dfs(Graph G, int v, int u) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w, v);
            } else if (w != u) {
                hasCycle = true;
            }
        }
    }

    /**
     * 检查图是否有环
     *
     * @return {@code true} 有环
     *         {@code false} 无环
     */
    public boolean hasCycle() {
        return hasCycle;
    }
}
