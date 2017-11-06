package base;

import org.junit.Assert;
import org.junit.Test;

public class BagTest {
    @Test
    public void addTest() {
        Bag<Integer> bag = new Bag<>();
        for (int i = 0; i < 10; i++) {
            bag.add(i);
        }

        Assert.assertNotNull(bag.iterator().next());
    }
}
