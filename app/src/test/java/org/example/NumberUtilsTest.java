package org.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NumberUtilsTest {

    @Test
    public void testEvensOnly() {
        int[] result = NumberUtils.evensOnly(new int[]{1, 2, 3, 4, 5});
        assertArrayEquals(new int[]{2, 4}, result);
    }

    @Test
    public void testOddsOnly() {
        int[] result = NumberUtils.oddsOnly(new int[]{1, 2, 3, 4, 5});
        assertArrayEquals(new int[]{1, 3, 5}, result);
    }

    @Test
    public void testAddFive() {
        int[] result = NumberUtils.addFive(new int[]{1, 2, 3});
        assertArrayEquals(new int[]{6, 7, 8}, result);
    }

    @Test
    public void testSquareNumbers() {
        int[] result = NumberUtils.squareNumbers(new int[]{1, 2, 3});
        assertArrayEquals(new int[]{1, 4, 9}, result);
    }
}
