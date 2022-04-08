package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testShardingAlgorithm() {
        // {((cid+1)%4).intdiv(2)}
        for (int i = 0; i < 100; i++) {
            int res = ((i + 1) % 4) / 2 + 1;
            System.out.println("主键：" + i + ", " + "分片计算：" + res);
        }
    }
}
