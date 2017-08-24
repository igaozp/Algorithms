package Graph;

/**
 * 深度优先搜索
 *
 * @author igaozp
 * @since 2017-07-12
 * @version 1.0
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
     * @param w 顶点
     * @return {@code true} 访问过
     *         {@code false} 未访问过
     */
    public boolean marked(int w) {
        return marked[w];
    }

    /**
     * 获取顶点的访问次数
     *
     * @return 访问次数
     */
    public int count() {
        return count;
    }
}
