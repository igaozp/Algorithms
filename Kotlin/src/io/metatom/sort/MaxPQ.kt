package io.metatom.sort

/**
 * 基于堆的优先队列
 *
 * 优先队列由一个基于堆的完全二叉树表示，
 * 二叉树的每个节点元素都大于它的子节点元素，根节点为二叉树的最大节点
 *
 * @author igaozp
 * @since 2017-09-02
 * @version 1.0
 */
class MaxPQ<Key : Comparable<Key>>(maxN: Int) {
    /**
     * 存储基于堆的完全二叉树
     */
    private var pq: MutableList<Key?>? = null
    /**
     * 队列的大小
     */
    private var N = 0

    /**
     * 基于堆的优先队列的构造方法
     */
    init {
        this.pq = MutableList(maxN + 1) { null }
    }

    /**
     * 检查队列是否为空
     *
     * @return `true` 队列为空
     *         `false` 队列不为空
     */
    fun isEmpty(): Boolean = N == 0

    /**
     * 检查队列的大小
     *
     * @return 队列的大小
     */
    fun size(): Int = N

    /**
     * 向队列插入元素
     *
     * @param v 插入的元素
     */
    fun insert(v: Key) {
        pq?.set(++N, v)
        swim(N)
    }

    /**
     * 删除队列的最大元素
     *
     * @return  队列的最大元素
     */
    fun delMax(): Key {
        val max = pq?.get(1)
        exch(1, N--)
        pq?.set(N + 1, null)
        sink(1)
        return max!!
    }

    /**
     * 比较两个参数的大小
     *
     * @param i 比较的第一个参数
     * @param j 比较的第二个参数
     * @return `true` 第一个参数比第二个小
     *         `false` 第一个参数比第二个大
     */
    private fun less(i: Int, j: Int): Boolean {
        return pq?.get(i)!! < pq?.get(j)!!
    }

    /**
     * 交换元素
     *
     * @param i 交换的第一个元素的下标
     * @param j 交换的第二个元素的下标
     */
    private fun exch(i: Int, j: Int) {
        val t = pq?.get(i)
        pq?.set(i, pq!![j])
        pq?.set(j, t)
    }

    /**
     * 由下至上的堆的有序化的实现
     *
     * 如果堆的有序状态被打破，需要将新插入的节点与父节点交换来修复，
     * 交换后如果仍是无序状态，则重复该步骤
     *
     * @param k 需要有序化的元素下标
     */
    private fun swim(k: Int) {
        // 如果该节点大于父节点，则与父节点交换位置
        var k = k
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k)
            k /= 2
        }
    }

    /**
     * 由上至下的堆的有序化的实现
     *
     * 如果堆的有序状态被打破，需要将该节点与两个子节点中较大的子节点来交换，
     * 直到它的子节点都比它小或者到达堆的底部
     *
     * @param k 需要有序化的元素下标
     */
    private fun sink(k: Int) {
        var k = k
        while (2 * k <= N) {
            var j = 2 * k
            // 寻找子节点中较大的一个
            if (j < N && less(j, j + 1)) j++
            // 如果该节点大于子节点，则完成任务
            if (!less(k, j)) break
            // 与子节点交换
            exch(k, j)
            k = j
        }
    }
}