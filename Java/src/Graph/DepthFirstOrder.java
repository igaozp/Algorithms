package Graph;

import edu.princeton.cs.algs4.Queue;
import java.util.Stack;

/**
 * 有向图中基于深度优先搜索的顶点排序
 *
 * @author igaozp
 * @since 2017-07-14
 * @version 1.0
 */
public class DepthFirstOrder {
    /**
     * 存储顶点的访问 状态
     */
    private boolean[] marked;
    /**
     * 顶点的先序排列
     */
    private Queue<Integer> pre;
    /**
     * 顶点的后续排列
     */
    private Queue<Integer> post;
    /**
     * 顶点的逆后序排列
     */
    private Stack<Integer> reversePost;

    /**
     * 构造方法
     *
     * @param G 有向图
     */
    public DepthFirstOrder(Digraph G) {
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();
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
     * @param G 有向图
     * @param v 起始顶点
     */
    private void dfs(Digraph G, int v) {
        pre.enqueue(v);
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }

        post.enqueue(v);
        reversePost.push(v);
    }

    /**
     * 获取顶点的先序排列
     *
     * @return 先序排列集合
     */
    public Iterable<Integer> pre() {
        return pre;
    }

    /**
     * 获取顶点的后序排列
     *
     * @return 后序排列集合
     */
    public Iterable<Integer> post() {
        return post;
    }

    /**
     * 获取顶点的逆后序排列
     *
     * @return 逆后序排列集合
     */
    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
