package base;

import org.junit.Assert;
import org.junit.Test;

/**
 * Stack 栈的单元测试
 *
 * @author igaozp
 * @version 1.0
 * @since 2017-11-10
 */
public class StackTest {
    @Test
    public void stackTest() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        Assert.assertEquals(10, stack.size());
        Assert.assertEquals(false, stack.isEmpty());

        for (int i = 9; i >= 0; i--) {
            int num = stack.pop();
            Assert.assertEquals(i, num);
        }
        Assert.assertEquals(true, stack.isEmpty());
    }
}
