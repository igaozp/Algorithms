package io.metatom.graph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.*;

import java.util.Stack;

/**
 * 有向图中基于深度优先搜索的顶点排序
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-14
 */
public class DepthFirstOrder {
    /**
     * 存储顶点的访问状态
     */
    private boolean[] marked;
    /**
     * 存放先序序列相应节点的数字
     */
    private int[] pre;
    /**
     * 存放后序序列相应节点的数字
     */
    private int[] post;
    /**
     * 顶点的先序排列
     */
    private Queue<Integer> preOrder;
    /**
     * 顶点的后序排列
     */
    private Queue<Integer> postOrder;
    /**
     * 先序遍历序列编号的计数器
     */
    private int preCounter;
    /**
     * 后序遍历序列编号的计数器
     */
    private int postCounter;

    /**
     * 构造方法
     *
     * @param G 有向图
     */
    public DepthFirstOrder(Digraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        preOrder = new Queue<>();
        postOrder = new Queue<>();
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
        assert check();
    }

    /**
     * 构造方法
     *
     * @param G 带权重的有向图
     */
    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postOrder = new Queue<>();
        preOrder = new Queue<>();
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
        preOrder.enqueue(v);
        marked[v] = true;
        pre[v] = preCounter++;

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }

        postOrder.enqueue(v);
        post[v] = postCounter++;
    }

    /**
     * 深度优先搜索
     *
     * @param G 带权重的有向图
     * @param v 起始顶点
     */
    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preOrder.enqueue(v);

        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (!marked[w]) {
                dfs(G, w);
            }
        }

        postOrder.enqueue(v);
        post[v] = postCounter++;
    }

    /**
     * 获取后序遍历序列指定顶点的遍历次数
     *
     * @param v 指定的顶点
     * @return 后序遍历的次数
     */
    public int post(int v) {
        validateVertex(v);
        return post[v];
    }

    /**
     * 获取先序遍历序列指定顶点的遍历次数
     *
     * @param v 指定的顶点
     * @return 先序遍历的次数
     */
    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }

    /**
     * 获取顶点的先序排列
     *
     * @return 先序排列集合
     */
    public Iterable<Integer> pre() {
        return preOrder;
    }

    /**
     * 获取顶点的后序排列
     *
     * @return 后序排列集合
     */
    public Iterable<Integer> post() {
        return postOrder;
    }

    /**
     * 获取顶点的逆后序排列
     *
     * @return 逆后序排列集合
     */
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<>();
        for (int v : postOrder) {
            reverse.push(v);
        }
        return reverse;
    }

    /**
     * 检查相关函数和功能是否正常
     *
     * @return {@code true} 正常
     * {@code false} 不正常
     */
    private boolean check() {
        int r = 0;
        for (int v : post()) {
            if (post(v) != r) {
                StdOut.println("post(v) and post() inconsistent");
                return false;
            }
            r++;
        }

        r = 0;
        for (int v : pre()) {
            if (pre(v) != r) {
                StdOut.println("pre(v) and pre() inconsistent");
                return false;
            }
            r++;
        }

        return true;
    }

    /**
     * 检查顶点
     *
     * @param v 指定的顶点
     */
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V + 1));
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        DepthFirstOrder dfs = new DepthFirstOrder(G);
        StdOut.println("   v pre post");
        StdOut.println("-------------");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
        }

        StdOut.print("PreOrder:  ");
        for (int v : dfs.pre()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("PostOrder:  ");
        for (int v : dfs.post()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("Reverse PostOrder:  ");
        for (int v : dfs.reversePost()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
    }
}
