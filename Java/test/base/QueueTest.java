package base;

import org.junit.Assert;
import org.junit.Test;

/**
 * Queue 队列的单元测试
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-11-10
 */
public class QueueTest {
    @Test
    public void queueTest() {
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        Assert.assertEquals(10, queue.size());
        Assert.assertEquals(false, queue.isEmpty());

        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int num = queue.dequeue();
            Assert.assertEquals(i, num);
        }
        Assert.assertEquals(true, queue.isEmpty());
    }
}
