package Find;

import Base.Queue;

/**
 * 顺序查找（基于无序链表）
 *
 * 使用无序链表构造查找表，表的每一个节点包含一个键和值，通过使用键来查找相应的值
 *
 * @author igaozp
 * @since 2017-07-04
 * @version 1.0
 *
 * @param <Key> 用于 key 的泛型类型
 * @param <Value> 用于 value 的泛型类型
 */
public class SequentialSearchST<Key, Value> {
    /**
     * 链表首节点
     */
    private Node first;

    /**
     * 内部定义的链表节点
     */
    private class Node {
        // 节点的键
        Key key;
        // 节点的值
        Value val;
        // 下一个节点
        Node next;

        /**
         * 节点的构造函数
         *
         * @param key 节点的键
         * @param val 节点的值
         * @param next 下一个节点
         */
        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 通过键 key 获取节点值
     *
     * @param key 需要查找的键
     * @return 查找到的值
     */
    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.val;
            }
        }
        return null;
    }

    /**
     * 查找给定的键，找到则更新其值，否则在表中新建节点
     *
     * @param key 需要查找的键
     * @param val 需要更新或插入的值
     */
    public void put(Key key, Value val) {
        for (Node x = first; x != null; x = x.next) {
            // 查找到更新节点的值
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        // 没有查找到，则新建节点
        first = new Node(key, val, first);
    }

    /**
     * 删除给定键的节点
     *
     * @param key 需要删除的节点的键
     */
    public void delete(Key key) {
        // 删除的节点的前一个节点
        Node prev = first;
        for (Node x = first; x != null; x = x.next) {
            if (x != first) {
                prev = prev.next;
            }
            if (key.equals(x.key)) {
                x.val = null;
                prev.next = x.next;
            }
        }
    }

    /**
     * 检查是否存在给定键的节点
     *
     * @param key 需要查找的键
     * @return {@code true} 节点存在
     *         {@code false} 节点不存在
     */
    public boolean contains(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查链表是否为空
     *
     * @return {@code true} 链表为空
     *         {@code false} 链表不为空
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * 检查链表的长度
     *
     * @return 链表长度
     */
    public int size() {
        int size = 0;
        for (Node x = first; x != null; x = x.next) {
            size++;
        }
        return size;
    }

    /**
     * 表中所有键的集合
     *
     * @return 包含所有键的队列
     */
    public Iterable<Key> keys() {
        // 所有的键保存到队列中
        Queue<Key> q = new Queue<>();
        for (Node x = first; x != null; x = x.next) {
            q.enqueue(x.key);
        }
        return q;
    }
}
