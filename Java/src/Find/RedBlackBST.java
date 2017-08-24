package Find;

/**
 * 红黑二叉查找树
 *
 * 红黑树是一种二叉树，且红链接均为左链接，没有任何一个节点同时和两条红色链接相连，
 * 该树是完美黑色平衡的，即任意链接到根节点的路径上的黑链接数量相同。
 *
 * @author igaozp
 * @since 2017-07-07
 * @version 1.0
 *
 * @param <Key> 用作 key 的泛型类型
 * @param <Value> 用作 value 的泛型类型
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    /**
     * 红黑树节点颜色，使用 true 代表红色，false 代表黑色
     */
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * 红黑树的根节点
     */
    private Node root;

    /**
     * 用于红黑树的内部节点
     */
    private class Node {
        Key key;
        Value val;
        Node left, right;
        // 子树中的节点总数
        int N;
        // 父节点此节点的链接颜色
        boolean color;

        /**
         * 节点的构造函数
         *
         * @param key 节点的键
         * @param val 节点的值
         * @param N 子树的节点总数
         * @param color 父节点指向的颜色
         */
        Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    /**
     * 检查红黑树的节点数量
     *
     * @return 红黑树的节点数量
     */
    public int size() {
        return size(root);
    }

    /**
     * 检查指定节点的子节点的数量
     *
     * @param x 需要检查的节点
     * @return 节点数量
     */
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    /**
     * 检查红黑树是否为空
     *
     * @return {@code true} 红黑树为空
     *         {@code false} 红黑树不为空
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * 检查指定节点的颜色是否为红色
     *
     * @param x 需要检查的节点
     * @return {@code true} 节点为红色
     *         {@code false} 节点为黑色
     */
    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    /**
     * 节点左旋
     *
     * @param h 需要旋转的节点
     * @return 旋转后的节点
     */
    public Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 节点右旋
     *
     * @param h 需要旋转的节点
     * @return 旋转后的节点
     */
    public Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 转换链接的颜色
     *
     * @param h 需要转换的节点
     */
    public void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 向红黑树中添加节点
     *
     * @param key 添加的节点的键
     * @param val 添加的节点的值
     */
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    /**
     * 向红黑树的指定节点下添加新的节点
     *
     * @param h 指定的节点
     * @param key 添加节点的键
     * @param val 添加节点的值
     * @return 添加节点的指定节点
     */
    private Node put(Node h, Key key, Value val) {
        if (h == null) {
            return new Node(key, val, 1, RED);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = put(h.left, key, val);
        } else if (cmp > 0) {
            h.right = put(h.right, key, val);
        } else {
            h.val = val;
        }

        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * 在红黑树的指定节点下删除相应的节点
     *
     * @param h 指定的节点
     * @param key 删除节点的键
     * @return 删除节点的指定节点
     */
    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }

            if (key.compareTo(h.key) == 0) {
                h.val = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }

        return balance(h);
    }

    /**
     * 删除红黑树中最小的节点
     */
    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 删除指定节点下最小的节点
     *
     * @param h 指定的节点
     * @return 删除节点的指定节点
     */
    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * 假设节点 h 为红色，h.left 和 h.left.left 都是黑色，
     * 将节点 h.left 或 h.left 的子节点变红
     *
     * @param h 指定的节点
     * @return 转换后指定的节点
     */
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    /**
     * 假设节点 h 为红色，h.right 和 h.right.right 都是黑色，
     * 将 h.right 或 h.right 的子节点变红
     *
     * @param h 指定的节点
     * @return 转换后指定的节点
     */
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (!isRed(h.left.left)) {
            h = rotateRight(h);
        }
        return h;
    }

    /**
     * 删除红黑树中最大的节点
     */
    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 删除指定节点下最大的节点
     *
     * @param h 指定的节点
     * @return 删除后指定的节点
     */
    private Node deleteMax(Node h) {
        if (isRed(h.left)) {
            h = rotateRight(h);
        }
        if (h.right == null) {
            return null;
        }
        if (!isRed(h.right) && !isRed(h.right.left)) {
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }

    /**
     * 将指定节点的子树重新平衡
     *
     * @param h 需要平衡的节点
     * @return 平衡后的节点
     */
    private Node balance(Node h) {
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    /**
     * 获取红黑树最小元素的键
     *
     * @return 最小元素的键
     */
    public Key min() {
        return min(root).key;
    }

    /**
     * 获取指定元素下最小的节点
     *
     * @param h 指定的元素
     * @return 指定元素的最小节点
     */
    private Node min(Node h) {
        if (h.left == null) {
            return h;
        }
        return min(h.left);
    }

    /**
     * 获取红黑树最大元素的键
     *
     * @return 最大元素的键
     */
    public Key max() {
        return max(root).key;
    }

    /**
     * 获取指定节点下最大的节点
     *
     * @param h 指定的节点
     * @return 指定元素的最大节点
     */
    private Node max(Node h) {
        if (h.right == null) {
            return h;
        }
        return max(h.right);
    }

    /**
     * 通过指定的键从红黑树中获取相应的值
     *
     * @param key 需要获取值的键
     * @return 相应键的值
     */
    public Value get(Key key) {
        return get(root, key);
    }

    /**
     * 从指定节点下根据键获取相应的值
     *
     * @param h 指定的节点
     * @param key 需要获取值的键
     * @return 相应键的值
     */
    private Value get(Node h, Key key) {
        if (h == null) {
            return null;
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            return get(h.left, key);
        } else if (cmp > 0) {
            return get(h.right, key);
        } else {
            return h.val;
        }
    }
}
