package Graph;

/**
 * 计算强连通分量的 Kosaraju 算法
 *
 * @author igaozp
 * @since 2017-07-15
 * @version 1.0
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
     *         {@code false} 不是强连通分量
     */
    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    /**
     * 获取指定顶点的标识符
     *
     * @param v 指定的顶点
     * @return 标识符
     */
    public int id(int v) {
        return id[v];
    }

    /**
     * 获取强连通分量的数量
     *
     * @return
     */
    public int count() {
        return count;
    }
}
