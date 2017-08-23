package String;

import java.math.BigInteger;
import java.util.Random;

/**
 * Rabin-Karp 指纹字符串查找算法
 *
 * @author igaozp
 * @since 2017-07-29
 * @version 1.0
 */
public class RabinKarp {
    /**
     * 用于匹配的模式字符串
     */
    private String pat;
    /**
     * 模式字符串的散列值
     */
    private long patHash;
    /**
     * 模式字符串的长度
     */
    private int M;
    /**
     * 一个很大的素数
     */
    private int Q;
    /**
     * 字母表的大小
     */
    private int R = 256;
    /**
     * R ^ (M - 1) % Q
     */
    private long RM;

    /**
     * 构造方法
     *
     * @param pat 模式字符串
     */
    public RabinKarp(String pat) {
        this.pat = pat;
        this.M = pat.length();
        Q = (int)longRandomPrime();
        RM = 1;
        for (int i = 1; i <= M - 1; i++) {
            RM = (R * RM) % Q;
        }
        patHash = hash(pat, M);
    }

    /**
     * 生成一个长的随机素数
     *
     * @return 素数
     */
    private static long longRandomPrime()
    {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    /**
     * 检查模式是否匹配
     *
     * @param i 下标
     * @return {@code true} 匹配
     *         {@code false} 不匹配
     */
    public boolean check(int i) {
        return true;
    }

    /**
     * 生成哈希值
     *
     * @param key 键
     * @param M 用于生成哈希值的键的位数
     * @return 哈希值
     */
    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++) {
            h = (R * h + key.charAt(j)) % Q;
        }
        return h;
    }

    /**
     * 字符串搜素
     *
     * @param txt 用作搜索的文本
     * @return 搜索到的字符串下标
     */
    private int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        if (patHash == txtHash && check(0)) {
            return 0;
        }
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (patHash == txtHash) {
                if (check(i - M + 1)) {
                    return i - M + 1;
                }
            }
        }
        return N;
    }
}
