package Graph;

/**
 * 加权有向图的边
 *
 * @author igaozp
 * @since 2017-07-18
 * @version 1.0
 */
public class DirectedEdge {
    /**
     * 边的起点
     */
    private final int v;
    /**
     * 边的终点
     */
    private final int w;
    /**
     * 边的权重
     */
    private final double weight;

    /**
     * 构造方法
     *
     * @param v 边的起点
     * @param w 边的终点
     * @param weight 边的权重
     */
    public DirectedEdge(int v, int w, double weight) {
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
     * 获取边的起点
     *
     * @return 边的起点
     */
    public int from() {
        return v;
    }

    /**
     * 获取边的终点
     *
     * @return 边的终点
     */
    public int to() {
        return w;
    }

    public String toString() {
        return String.format("%d->%d %.2f", v, w, weight);
    }
}
