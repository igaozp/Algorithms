package find

import edu.princeton.cs.algs4.Queue

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
class KLinearProbingHashST<K, V>(cap: Int) {
    // 键值对的数量
    private var N = 0
    // 散列表的大小
    private var M = 16
    // 存放键的数组
    private var keys: MutableList<K?>
    // 存放值的数组
    private var vals: MutableList<V?>

    // 构造函数
    init {
        this.M = cap
        this.N = 0
        keys = MutableList(cap, { null })
        vals = MutableList(cap, { null })
    }

    // 散列表的大小
    fun size() = N

    // 检查散列表是否为空
    fun isEmpty() = size() == 0

    // 哈希函数
    private fun hash(key: K?): Int = (key!!.hashCode() and 0x7fffffff) % M

    // 改变散列表的大小
    private fun resize(cap: Int) {
        val t = KLinearProbingHashST<K, V>(cap)
        (0 until M)
                .filter { keys[it] != null }
                .forEach { t.put(keys[it], vals[it]) }
        keys = t.keys
        vals = t.vals
        M = t.M
    }

    // 添加新的键值对
    fun put(key: K?, value: V?) {
        if (key == null) throw IllegalArgumentException("first argument to put() is null")
        if (value == null) {
            delete(key)
            return
        }
        // 如果没有充足的大小，则进行扩容
        if (N >= M / 2) resize(2 * M)

        // 对键值对的键生成哈希值，通过哈希值对数组进行索引，
        // 如果有冲突则进行线性探测，直到没有冲突
        var i = hash(key)
        while (keys[i] != null) {
            if (keys[i] == key) {
                vals[i] = value
                return
            }
            i = (i + 1) % M
        }

        // 将键值对存入合适的位置，并更新散列表大小
        keys[i] = key
        vals[i] = value
        N++
    }

    // 通过键查找相应的值
    fun get(key: K?): V? {
        if (key == null) throw IllegalArgumentException("argument to get() is null")
        var i = hash(key)
        while (keys[i] != null) {
            if (keys[i] == key) {
                return vals[i]
            }
            i = (i + 1) % M
        }
        return null
    }

    // 通过键删除相应的键值对
    fun delete(key: K?) {
        if (key == null) throw IllegalArgumentException("argument to delete() is null")

        // 检查该键是否存在
        if (!contains(key)) return

        // 生成哈希值
        var i = hash(key)
        // 寻找存放位置
        while (key != keys[i]) i = (i + 1) % M
        // 清除删除的键值对对象
        keys[i] = null
        vals[i] = null

        // 将之后的键值对前移
        i = (i + 1) % M
        while (keys[i] != null) {
            val keyToRedo = keys[i]
            val valToRedo = vals[i]
            keys[i] = null
            vals[i] = null
            N--
            put(keyToRedo, valToRedo)
            i = (i + 1) % M
        }

        // 更新散列表尺寸
        N--
        if (N > 0 && N == M / 8) resize(M / 2)

        assert(check())
    }

    // 检查键在散列表中是否存在
    fun contains(key: K): Boolean = get(key) != null

    // 获取哈希表键的集合
    fun keys(): Iterable<K> {
        val queue = Queue<K>()
        for (i in 0 until M) {
            if (keys[i] != null) queue.enqueue(keys[i])
        }
        return queue
    }

    // 检查哈希表是否合法
    private fun check(): Boolean {
        if (M < 2 * N) {
            println("Hash table size m = $M; array size n = $N")
            return false
        }

        for (i in 0 until M) {
            if (keys[i] == null) {
                continue
            } else if (get(keys[i]) != vals[i]) {
                println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i])
                return false
            }
        }
        return true
    }
}

// 单元测试
fun main(args: Array<String>) {
    val hashTable = KLinearProbingHashST<String, Int>(10)
    for (i in 0 until 20) {
        hashTable.put(i.toString(), i)
    }
    println("size of hash table = " + hashTable.size())

    for (i in hashTable.keys()) {
        print(i + "\t")
    }
}