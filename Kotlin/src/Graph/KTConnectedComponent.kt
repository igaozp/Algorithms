package Graph

/**
 * 使用深度优先搜索查找图中的连通分量
 *
 * @author igaozp
 * @since 2017-09-08
 * @version 1.0
 */
class KTConnectedComponent(G: Graph) {
    /**
     * 存储图中顶点的访问状态
     */
    private var marked: Array<Boolean>? = null
    /**
     * 连通分量的标识符
     */
    private var id: Array<Int>? = null
    /**
     * 顶点的访问次数
     */
    private var count: Int = 0

    /**
     * 构造方法
     */
    init {
        marked = Array(G.V())
        id = Array(G.V())

        for (s in 0 until G.V()) {
            if (!marked!![s]) {
                dfs(G, s)
                count++
            }
        }
    }

    /**
     * 生成函数的辅助数组
     */
    private fun <T> Array(size: Int): Array<T> = Array(size)

    /**
     * 深度优先搜索
     *
     * @param G 搜索的图
     * @param v 起始顶点
     */
    private fun dfs(G: Graph, v: Int) {
        marked!![v] = true
        id!![v] = count

        // 访问与 v 连通的顶点
        G.adj(v)
                .filterNot { marked!![it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 检查两个连通分量
     *
     * @param v 连通分量
     * @param w 连通分量
     * @return `true` 两个分量连通
     *         `false` 两个分量不连通
     */
    fun connected(v: Int, w: Int): Boolean = id!![v] == id!![w]

    /**
     * 检查某个顶点的连通分量的标识符
     *
     * @param v 指定的顶点
     * @return 标识符
     */
    fun id(v: Int): Int = id!![v]

    /**
     * 图的访问次数
     *
     * @return 访问次数
     */
    fun count(): Int = count
}