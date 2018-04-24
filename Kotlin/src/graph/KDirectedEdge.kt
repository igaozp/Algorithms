package graph

/**
 * 加权有向图的边
 *
 * @author igaozp
 * @since 2017-09-18
 * @version 1.0
 */
class KDirectedEdge(private var v: Int, private var w: Int, private var weight: Double) {
    /**
     * 构造方法
     */
    init {
        if (v < 0 || w < 0) {
            throw IllegalArgumentException("Vertex names must be nonnegative integers")
        }
        if (weight.isNaN()) {
            throw IllegalArgumentException("Weight is NaN")
        }
    }

    /**
     * 获取边的权重
     *
     * @return 边的权重
     */
    fun weight(): Double = weight

    /**
     * 获取边的起点
     *
     * @return 边的起点
     */
    fun from(): Int = v

    /**
     * 获取边的终点
     *
     * @return 边的终点
     */
    fun to(): Int = w

    override fun toString(): String = String.format("$v -> $w $weight")
}

/**
 * 单元测试
 */
fun main(args: Array<String>) {
    val edge = KDirectedEdge(12, 34, 5.67)
    println(edge)
}