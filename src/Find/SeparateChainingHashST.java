package Find;

import Base.Queue;

/**
 * 基于拉链法的散列表
 *
 * 将数组中的每一元素指向一条链表，链表中的每一节点都存储了散列值为该元素的索引的键值对
 *
 * @author igaozp
 * @since 2017-07-10
 * @version 1.0
 *
 * @param <Key> 泛型类型
 * @param <Value> 泛型类型
 */
public class SeparateChainingHashST<Key, Value> {
    /**
     * 键值对总数
     */
    private int N;
    /**
     * 散列表的大小
     */
    private int M;
    /**
     * 存放链表对象的数组
     */
    private SequentialSearchST<Key, Value>[] st;

    /**
     * 无参构造方法
     */
    public SeparateChainingHashST() {
        this(997);
    }

    /**
     * 带有参数的构造方法
     *
     * @param M 初始化的散列表大小
     */
    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST();
        }
    }

    /**
     * 哈希函数，获取键值对中键的哈希值
     *
     * @param key 键值对的键
     * @return 哈希值
     */
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * 根据键获取相应的值
     *
     * @param key 需要获取值的键
     * @return 键对应的值
     */
    public Value get(Key key) {
        return (Value) st[hash(key)].get(key);
    }

    /**
     * 向散列表中添加新的键值对
     *
     * @param key 键值对的键
     * @param val 键值对的值
     */
    public void put(Key key, Value val) {
        st[hash(key)].put(key, val);
    }

    /**
     * 获取散列表中所有键的集合
     *
     * @return 包含键的队列
     */
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            Queue<Key> temp = (Queue<Key>) st[i].keys();
            for (Key key : temp) {
                q.enqueue(key);
            }
        }
        return q;
    }
}
