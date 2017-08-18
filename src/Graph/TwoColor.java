package Graph;

/**
 * 双色问题
 *
 * 双色问题：能够用两种颜色将图的所有顶点着色，
 * 使得任意一条边的两个端点的颜色都不相同
 *
 * @author igaozp
 * @since 2017-07-13
 * @version 1.0
 */
public class TwoColor {
    /**
     * 存储顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 存储顶点的颜色状态
     */
    private boolean[] color;
    /**
     * 是否是二分图
     */
    private boolean isTwoColorable = true;

    /**
     * 构造方法
     *
     * @param G 初始化的图
     */
    public TwoColor(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];

        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
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

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(G, w);
            } else if (color[w] == color[v]) {
                isTwoColorable = false;
            }
        }
    }

    /**
     * 是否是二分图
     *
     * @return {@code true} 是二分图
     *         {@code false} 不是二分图
     */
    public boolean isBipartite() {
        return isTwoColorable;
    }
}
