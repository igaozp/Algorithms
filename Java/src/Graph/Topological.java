package Graph;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolDigraph;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.DepthFirstOrder;

/**
 * 拓扑排序
 *
 * @author igaozp
 * @since 2017-07-14
 * @version 1.0
 */
public class Topological {
    /**
     * 拓扑序列
     */
    private Iterable<Integer> order;

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
     *         {@code false} 不是有向无环图
     */
    public boolean isDAG() {
        return order != null;
    }

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
