package graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

/**
 * 拓扑排序
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-14
 */
public class Topological {
    /**
     * 拓扑序列
     */
    private Iterable<Integer> order;
    /**
     * rank[v] = rank of vertex v in order
     */
    private int[] rank;

    /**
     * 拓扑排序的构造方法
     *
     * @param G 有向图
     */
    public Topological(Digraph G) {
        DirectedCycle cycleFinder = new DirectedCycle(G);

        // 检查是否有环，若没有则进行拓扑排序
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
            rank = new int[G.V()];
            int i = 0;
            for (int v : order) {
                rank[v] = i++;
            }
        }
    }

    /**
     * 构造方法
     *
     * @param G 带权重的有向图
     */
    public Topological(EdgeWeightedDigraph G) {
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    /**
     * 获取拓扑序列
     *
     * @return 拓扑序列
     */
    public Iterable<Integer> order() {
        return order;
    }

    /**
     * 检查是否是有向无环图
     *
     * @return {@code true} 是有向无环图
     * {@code false} 不是有向无环图
     */
    public boolean isDAG() {
        return order != null;
    }

    /**
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private void validateVertex(int v) {
        int V = rank.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String filename = args[0];
        String separator = args[0];
        SymbolDigraph sg = new SymbolDigraph(filename, separator);
        Topological top = new Topological(sg.G());
        for (int v : top.order()) {
            StdOut.println(sg.name(v));
        }
    }
}
