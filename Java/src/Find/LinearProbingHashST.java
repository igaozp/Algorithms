package Find;

/**
 * 基于线性探测的散列表
 *
 * 利用大小为 M 的数组存放 N 个键值对，其中 M > N，
 * 当发生哈希冲突时，依次检查散列表的下一个位置，直到没有冲突
 *
 * @author igaozp
 * @since 2017-07-10
 * @version 1.0
 *
 * @param <Key> 泛型类型
 * @param <Value> 泛型类型
 */
public class LinearProbingHashST<Key, Value> {
    /**
     * 键值对的数量
     */
    private int N;
    /**
     * 散列表的大小
     */
    private int M = 16;
    /**
     * 存放键的数组
     */
    private Key[] keys;
    /**
     * 存放值的数组
     */
    private Value[] vals;

    /**
     * 构造函数
     *
     * @param cap 散列表初始化大小
     */
    public LinearProbingHashST(int cap) {
        keys = (Key[]) new Object[cap];
        vals = (Value[]) new Object[cap];
    }

    /**
     * 哈希函数
     *
     * @param key 键值对的键
     * @return 哈希值
     */
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * 改变散列表的大小
     *
     * @param cap 新的散列表大小
     */
    private void resize(int cap) {
        LinearProbingHashST<Key, Value> t;
        t = new LinearProbingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                t.put(keys[i], vals[i]);
            }
        }
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    /**
     * 添加新的键值对
     *
     * @param key 键值对的键
     * @param val 键值对的值
     */
    public void put(Key key, Value val) {
        // 如果没有充足的大小，则进行扩容
        if (N >= M / 2) {
            resize(2 * M);
        }

        /*
        对键值对的键生成哈希值，通过哈希值对数组进行索引，
        如果有冲突则进行线性探测，直到没有冲突
         */
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }

        // 将键值对存入合适的位置，并更新散列表大小
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    /**
     * 通过键查找相应的值
     *
     * @param key 查找的键
     * @return 查找到的值
     */
    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    /**
     * 通过键删除相应的键值对
     *
     * @param key 删除的键值对的键
     */
    public void delete(Key key) {
        // 检查该键是否存在
        if (!contains(key)) {
            return;
        }

        // 生成哈希值
        int i = hash(key);
        // 寻找存放位置
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        // 清除删除的键值对对象
        keys[i] = null;
        vals[i] = null;

        // 将之后的键值对前移
        i = (i + 1) % M;
        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }

        // 更新散列表尺寸
        N--;
        if (N > 0 && N == M / 8) {
            resize(M / 2);
        }
    }

    /**
     * 检查键在散列表中是否存在
     *
     * @param key 需要检查的键
     * @return {@code true} 存在
     *         {@code false} 不存在
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }
}
