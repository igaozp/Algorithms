package Find

/**
 * 基于线性探测的散列表
 *
 * 利用大小为 M 的数组存放 N 个键值对，其中 M > N，
 * 当发生哈希冲突时，依次检查散列表的下一个位置，直到没有冲突
 *
 * @author igaozp
 * @since 2017-09-03
 * @version 1.0
 */
class KTLinearProbingHashST<K, V>(cap: Int) {
    /**
     * 键值对的数量
     */
    private var N = 0
    /**
     * 散列表的大小
     */
    private var M = 16
    /**
     * 存放键的数组
     */
    private var keys: MutableList<K?>? = null
    /**
     * 存放值的数组
     */
    private var vals: MutableList<V?>? = null

    /**
     * 构造函数
     */
    init {
        keys = MutableList(cap, { null })
        vals = MutableList(cap, { null })
    }

    /**
     * 哈希函数
     *
     * @param key 键值对的键
     * @return 哈希值
     */
    private fun hash(key: K?): Int = (key!!.hashCode() and 0x7fffffff) % M

    /**
     * 改变散列表的大小
     *
     * @param cap 新的散列表大小
     */
    private fun resize(cap: Int) {
        val t = KTLinearProbingHashST<K, V>(cap)
        (0 until M)
                .filter { keys!![it] != null }
                .forEach { t.put(keys!![it], vals!![it]) }
        keys = t.keys
        vals = t.vals
        N = t.N
    }

    /**
     * 添加新的键值对
     *
     * @param key 键值对的键
     * @param value 键值对的值
     */
    fun put(key: K?, value: V?) {
        // 如果没有充足的大小，则进行扩容
        if (N >= M / 2) resize(2 * M)

        /*
        对键值对的键生成哈希值，通过哈希值对数组进行索引，
        如果有冲突则进行线性探测，直到没有冲突
         */
        var i = hash(key)
        while (keys!![i] != null) {
            if (keys!![i] == key) {
                vals!![i] = value
                return
            }
            i = (i + 1) % M
        }

        // 将键值对存入合适的位置，并更新散列表大小
        keys!![i] = key
        vals!![i] = value
        N++
    }

    /**
     * 通过键查找相应的值
     *
     * @param key 查找的键
     * @return 查找到的值
     */
    fun get(key: K): V? {
        var i = hash(key)
        while (keys!![i] != null) {
            if (keys!![i] == key) {
                return vals!![i]
            }
            i = (i + 1) % M
        }
        return null
    }

    /**
     * 通过键删除相应的键值对
     *
     * @param key 删除的键值对的键
     */
    fun delete(key: K) {
        // 检查该键是否存在
        if (!contains(key)) return

        // 生成哈希值
        var i = hash(key)
        // 寻找存放位置
        while (key != keys!![i]) {
            i = (i + 1) % M
        }
        // 清除删除的键值对对象
        keys!![i] = null
        vals!![i] = null

        // 将之后的键值对前移
        i = (i + 1) % M
        while (keys!![i] != null) {
            val keyToRedo = keys!![i]
            val valToRedo = vals!![i]
            keys!![i] = null
            vals!![i] = null
            N--
            put(keyToRedo, valToRedo)
            i = (i + 1) % M
        }

        // 更新散列表尺寸
        N--
        if (N > 0 && N == M / 8) resize(M / 2)
    }

    /**
     * 检查键在散列表中是否存在
     *
     * @param key 需要检查的键
     * @return {@code true} 存在
     *         {@code false} 不存在
     */
    fun contains(key: K): Boolean = get(key) != null
}