package Graph

import edu.princeton.cs.algs4.Edge

/**
 * 加权无向图的边
 *
 * @author igaozp
 * @since 2017-09-10
 * @version 1.0
 */
class KTEdge(private var v: Int, private var w: Int, private var weight: Double) : Comparable<Edge> {
    /**
     * 获取边的权重
     *
     * @return 边的权重
     */
    fun weight(): Double = weight

    /**
     * 获取边的一个顶点
     *
     * @return 顶点
     */
    fun either(): Int = v

    /**
     * 获取边的另一个顶点
     *
     * @param vertex 边的一个顶点
     * @return 边的另一个定点
     */
    fun other(vertex: Int): Int = when (vertex) {
        v -> w
        w -> v
        else -> throw RuntimeException("Inconsistent edge")
    }

    /**
     * 与另一条边比较权重
     *
     * @param other 另一条边
     * @return 比较结果
     */
    override fun compareTo(other: Edge): Int = when {
        this.weight < other.weight() -> -1
        this.weight > other.weight() -> 1
        else -> 0
    }

    override fun toString(): String = String.format("$v - $w $weight")
}