package Graph

class KTDirectedEdge(private var v: Int, private var w: Int, private var weight: Double) {
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