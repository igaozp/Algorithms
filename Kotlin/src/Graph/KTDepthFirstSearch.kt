package Graph

/**
 * 深度优先搜索
 *
 * @author igaozp
 * @since 2017-07-12
 * @version 1.0
 */
class KTDepthFirstSearch(G: Graph, s: Int) {
    /**
     * 存储图的每个顶点的访问情况
     */
    private var marked: Array<Boolean>? = null
    /**
     * 访问的次数
     */
    private var count: Int = 0

    /**
     * 构造方法
     */
    init {
        marked = Array(G.V())
        dfs(G, s)
    }

    /**
     * 生成数组的辅助函数
     */
    private fun <T> Array(size: Int): Array<T> = Array(size)

    /**
     * 深度优先搜索
     *
     * @param G 搜素的图
     * @param v 搜索的顶点
     */
    private fun dfs(G: Graph, v: Int) {
        marked!![v] = true
        count++
        G.adj(v)
                .filterNot { marked!![it] }
                .forEach { dfs(G, it) }
    }

    /**
     * 检查顶点的访问情况
     *
     * @param w 顶点
     * @return `true` 访问过
     *         `false` 未访问过
     */
    fun marked(w: Int): Boolean = marked!![w]

    /**
     * 获取顶点的访问次数
     *
     * @return 访问次数
     */
    fun count(): Int = count
}