package Find

import edu.princeton.cs.algs4.Queue

/**
 * 基于拉链法的散列表
 *
 * 将数组中的每一元素指向一条链表，链表中的每一节点都存储了散列值为该元素的索引的键值对
 *
 * @author igaozp
 * @since 2017-09-04
 * @version 1.0
 */
class KTSeparateChainingHashST<K, V>(private val M: Int) {
    /**
     * 键值对总数
     */
    private var N = 0
    /**
     * 存放链表对象的数组
     */
    private var st: Array<SequentialSearchST<K, V>>? = null

    /**
     * 构造函数
     */
    init {
        st = Array(M)
        for (i in 0 until M) {
            st!![i] = SequentialSearchST()
        }
    }

    /**
     * 初始化数组的辅助函数
     */
    private fun Array(size: Int): Array<SequentialSearchST<K, V>> = Array(size)

    /**
     * 哈希函数，获取键值对中键的哈希值
     *
     * @param key 键值对的键
     * @return 哈希值
     */
    private fun hash(key: K): Int = (key!!.hashCode() and 0x7fffffff) % M

    /**
     * 根据键获取相应的值
     *
     * @param key 需要获取值的键
     * @return 键对应的值
     */
    fun get(key: K): V = st!![hash(key)].get(key)

    /**
     * 向散列表中添加新的键值对
     *
     * @param key 键值对的键
     * @param value 键值对的值
     */
    fun put(key: K, value: V) {
        st!![hash(key)].put(key, value)
        N++
    }

    /**
     * 获取散列表中所有键的集合
     *
     * @return 包含键的队列
     */
    fun keys(): Iterable<K> {
        val q = Queue<K>()
        (0 until M)
                .map { st!![it].keys() as Queue<K> }
                .flatMap { it }
                .forEach { q.enqueue(it) }
        return q
    }
}