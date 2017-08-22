package String;

/**
 * 低位优先的字符排序算法
 *
 * @author igaozp
 * @since 2017-07-21
 * @version 1.0
 */
public class LSD {
    /**
     * 字符串排序
     *
     * 假设每一个字符串的长度相等，从每一个字符串的低位到高位进行排序，
     * 最后完成字符串的排序
     *
     * @param a 需要排序的字符串数组
     * @param W 字符串长度
     */
    public static void sort(String[] a, int W) {
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            // 计算出现的频率
            int[] count = new int[R + 1];
            for (String s : a) {
                count[s.charAt(d) + 1]++;
            }

            // 将频率转换为索引
            for (int r = 0; r < R; r++) count[r + 1] += count[r];

            // 将元素分类
            for (String s : a) {
                aux[count[s.charAt(d)]++] = s;
            }

            // 回写
            System.arraycopy(aux, 0, a, 0, N);
        }
    }
}
