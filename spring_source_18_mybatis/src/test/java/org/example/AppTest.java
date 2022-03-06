package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void testDate() {
        System.out.println(new Date());
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}
