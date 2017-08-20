package Graph;

/**
 * 加权无向图的边
 *
 * @author igaozp
 * @since 2017-07-16
 * @version 1.0
 */
public class Edge implements Comparable<Edge> {
    /**
     * 边的一个顶点
     */
    private final int v;
    /**
     * 边的另一个顶点
     */
    private final int w;
    /**
     * 边的权重
     */
    private final double weight;

    /**
     * 构造方法
     *
     * @param v 边的顶点
     * @param w 边的顶点
     * @param weight 边的权重
     */
    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * 获取边的权重
     *
     * @return 边的权重
     */
    public double weight() {
        return weight;
    }

    /**
     * 获取边的一个顶点
     *
     * @return 顶点
     */
    public int either() {
        return v;
    }

    /**
     * 获取边的另一个顶点
     *
     * @param vertex 边的一个顶点
     * @return 边的另一个定点
     */
    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new RuntimeException("Inconsistent edge");
        }
    }

    /**
     * 与另一条边比较权重
     *
     * @param that 另一条边
     * @return 比较结果
     */
    public int compareTo(Edge that) {
        if (this.weight() < that.weight()) {
            return -1;
        } else if (this.weight() > that.weight()) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
