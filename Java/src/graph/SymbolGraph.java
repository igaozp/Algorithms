package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 符号图
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-13
 */
public class SymbolGraph {
    /**
     * 符号表，用于索引
     */
    private ST<String, Integer> st;
    /**
     * 反向索引
     */
    private String[] keys;
    /**
     * 无向图
     */
    private Graph G;

    /**
     * 符号图的构造方法
     *
     * @param stream 输入流
     * @param sp     分隔符
     */
    public SymbolGraph(String stream, String sp) {
        st = new ST<>();
        In in = new In(stream);

        // 读取数据，并构造符号表
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(sp);
            for (String s : a) {
                if (!st.contains(s)) {
                    st.put(s, st.size());
                }
            }
        }

        // 构建反向索引
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }

        // 构建符号图
        G = new Graph(st.size());
        in = new In(stream);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(sp);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                G.addEdge(v, st.get(a[i]));
            }
        }
    }

    /**
     * 检查图中是否包含某个字符串
     *
     * @param s 指定的字符串
     * @return {@code true} 包含
     * {@code false} 不包含
     */
    public boolean contains(String s) {
        return st.contains(s);
    }

    /**
     * 获取指定字符串的索引
     *
     * @param s 指定的字符串
     * @return 字符串索引
     */
    public int index(String s) {
        if (contains(s)) {
            return st.get(s);
        } else {
            return -1;
        }
    }

    /**
     * 根据索引获取字符串
     *
     * @param v 指定的索引
     * @return 字符串
     */
    public String name(int v) {
        validateVertex(v);
        return keys[v];
    }

    /**
     * 获取符号图
     *
     * @return 符号图
     */
    public Graph G() {
        return G;
    }

    /**
     * 验证顶点
     *
     * @param v 需要验证的顶点
     */
    private void validateVertex(int v) {
        int V = G.V();
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }

    public static void main(String[] args) {
        String stream = args[0];
        String sp = args[1];
        SymbolGraph sg = new SymbolGraph(stream, sp);
        Graph graph = sg.G();
        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            if (sg.contains(source)) {
                int s = sg.index(source);
                for (int v : graph.adj(s)) {
                    StdOut.println("    " + sg.name(v));
                }
            } else {
                StdOut.println("input not contain '" + source + "'");
            }
        }
    }
}
