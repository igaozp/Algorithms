package io.metatom.graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import io.metatom.base.Queue;

import java.util.Stack;

/**
 * 使用广度优先搜索查找图中的路径
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-12
 */
@SuppressWarnings({"unused", "DuplicatedCode"})
public class BreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    /**
     * 标记图中顶点的访问情况
     */
    private boolean[] marked;
    /**
     * 一棵由父链接标识的树，从起点到一个顶点的已知路径上的最后一个顶点
     */
    private int[] edgeTo;
    /**
     * 存放路径的长度
     */
    private int[] distTo;

    /**
     * 构造方法
     *
     * @param G 初始化的图
     * @param s 开始顶点
     */
    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        validateVertex(s);
        bfs(G, s);

        assert check(G, s);
    }

    /**
     * 构造方法
     *
     * @param G       初始化的图
     * @param sources 源顶点集合
     */
    public BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        validateVertices(sources);
        bfs(G, sources);
    }

    /**
     * 广度优先搜索
     *
     * @param G 搜索的图
     * @param s 开始顶点
     */
    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    /**
     * 广度优先搜索
     *
     * @param G       搜索的图
     * @param sources 搜索的数据集合
     */
    private void bfs(Graph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * 检查特定顶点是否有相应的路径
     *
     * @param v 指定的顶点
     * @return {@code true} 有路径
     * {@code false} 没有路径
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 指定顶点的路径长度
     *
     * @param v 指定的顶点
     * @return 路径长度
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * 到指定顶点的路径
     *
     * @param v 指定的顶点
     * @return 路径
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }

    /**
     * 合法性检测
     *
     * @param G 检查的图
     * @param s 起始顶点
     * @return {@code true} 成功
     * {@code false} 不成功
     */
    private boolean check(Graph G, int s) {
        if (distTo[s] != 0) {
            StdOut.println("distances of sources " + s + " to itself = " + distTo[s]);
            return false;
        }

        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    StdOut.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("distTo[" + v + "] = " + distTo[v]);
                    StdOut.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }

        for (int w = 0; w < G.V(); w++) {
            if (!hasPathTo(w) || w == s) {
                continue;
            }
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                StdOut.println("shortest path edges " + v + "-" + w);
                StdOut.println("distTo[" + v + "] = " + distTo[v]);
                StdOut.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return false;
    }

    /**
     * 验证顶点
     *
     * @param v 顶点
     */
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || V >= v) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 验证顶点
     *
     * @param vertices 顶点的集合
     */
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
            }
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);

        int s = Integer.parseInt(args[1]);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo[v]);
                for (int x : bfs.pathTo(v)) {
                    if (x == s) {
                        StdOut.print(x);
                    } else {
                        StdOut.print("-" + x);
                    }
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d (-): not connected\n", s, v);
            }
        }
    }
}
