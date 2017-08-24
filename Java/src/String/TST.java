package String;

/**
 * 基于三向单词查找树的符号表
 *
 * @author igaozp
 * @since 2017-07-26
 * @version 1.0
 *
 * @param <Value> 泛型类型
 */
public class TST<Value> {
    /**
     * 根节点
     */
    private Node root;

    /**
     * 内部定义的节点类
     */
    private class Node {
        // 字符
        char c;

        // 左中右子树
        Node left;
        Node mid;
        Node right;

        // 相关联的值
        Value val;
    }

    /**
     * 根据字符串查找相关联的值
     *
     * @param key 用字符串表示的键
     * @return 相关联的值
     */
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return x.val;
    }

    /**
     * 在指定节点下查找字符串相关联的节点
     *
     * @param x 指定的节点
     * @param key 用字符串表示的键
     * @param d 记录查找字符串的字符位置
     * @return 查找到的节点
     */
    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        char c = key.charAt(d);

        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    /**
     * 向树中添加新的键值对
     *
     * @param key 键
     * @param val 值
     */
    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    /**
     * 在指定节点下添加键值对
     *
     * @param x 指定的节点
     * @param key 键
     * @param val 值
     * @param d 记录查找字符串的字符位置
     * @return 添加后的指定节点
     */
    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d + 1);
        } else {
            x.val = val;
        }
        return x;
    }
}
