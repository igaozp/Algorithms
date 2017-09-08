package Graph;

/**
 * 使用深度优先搜索查找图中的连通分量
 *
 * @author igaozp
 * @since 2017-07-13
 * @version 1.0
 */
public class ConnectedComponent {
    /**
     * 存储图中顶点的访问状态
     */
    private boolean[] marked;
    /**
     * 连通分量的标识符
     */
    private int[] id;
    /**
     * 顶点的访问次数
     */
    private int count;

    /**
     * 构造方法
     *
     * @param G 初始化的图
     */
    public ConnectedComponent(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];

        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
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
        id[v] = count;

        // 访问与 v 连通的顶点
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    /**
     * 检查两个连通分量
     *
     * @param v 连通分量
     * @param w 连通分量
     * @return {@code true} 两个分量连通
     *         {@code false} 两个分量不连通
     */
    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    /**
     * 检查某个顶点的连通分量的标识符
     *
     * @param v 指定的顶点
     * @return 标识符
     */
    public int id(int v) {
        return id[v];
    }

    /**
     * 图的访问次数
     *
     * @return 访问次数
     */
    public int count() {
        return count;
    }
}
