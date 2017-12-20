package graph;

import base.Queue;

/**
 * 图的广度优先搜索
 * 
 * @author igaozp
 * @since 2017-07-12
 * @version 1.0
 */
public class BreadthFirstSearch {
    /**
     * 存储图顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 存储顶点的访问次数
     */
    private int count;

    /**
     * 够着方法
     * 
     * @param G 初始化的图
     * @param s 搜索的起点
     */
    public BreadthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        bfs(G, s);
    }

    /**
     * 广度优先搜索
     * 
     * @param G 需要搜索的图
     * @param s 搜索的起点
     */
    private void bfs(Graph G, int s) {
        // 将访问过的元素加入队列
        Queue<Integer> queue = new Queue<>();
        marked[s] = true;
        queue.enqueue(s);

        // 依次访问以出队的元素为顶点的元素
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                // 没有访问则入队
                if (!marked[w]) {
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    /**
     * 检查指定的顶点是否访问过
     * 
     * @param w 指定的顶点
     * @return {@code true} 访问过
     *         {@code false} 未访问过
     */
    public boolean marked(int w) {
        return marked[w];
    }

    /**
     * 获取图的访问次数
     * 
     * @return 访问次数
     */
    public int count() {
        return count;
    }
}
