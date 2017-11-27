package find;

import edu.princeton.cs.algs4.BinarySearchST;
import org.junit.Assert;
import org.junit.Test;

/**
 * 二分搜索的单元测试
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-11-12
 */
public class BinarySearchSTTest {
    @Test
    public void binarySearchTest() {
        BinarySearchST<Integer, String> binarySearchST = new BinarySearchST<>(10);
        for (int i = 0; i < 10; i++) {
            binarySearchST.put(i, Integer.toString(i));
        }

        Assert.assertEquals(false, binarySearchST.isEmpty());
        Assert.assertEquals(10, binarySearchST.size());

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(Integer.toString(i), binarySearchST.get(i));
        }

        Assert.assertEquals(3, binarySearchST.rank(3));

        binarySearchST.delete(3);
        Assert.assertEquals(null, binarySearchST.get(3));
    }
}
