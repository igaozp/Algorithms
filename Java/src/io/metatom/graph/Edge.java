package io.metatom.graph;

import edu.princeton.cs.algs4.StdOut;

/**
 * 加权无向图的边
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-07-16
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
     * @param v      边的顶点
     * @param w      边的顶点
     * @param weight 边的权重
     */
    public Edge(int v, int w, double weight) {
        if (v < 0 || w < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
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
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }

    /**
     * 单元测试
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Edge e = new Edge(12, 34, 5.67);
        StdOut.println(e);
    }
}
