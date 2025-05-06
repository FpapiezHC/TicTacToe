import java.util.Arrays;

public class NumberUtils {
    public static int[] evensOnly(int[] numbers) {
        return Arrays.stream(numbers).filter(n -> n % 2 == 0).toArray();
    }

    public static int[] oddsOnly(int[] numbers) {
        return Arrays.stream(numbers).filter(n -> n % 2 != 0).toArray();
    }

    public static int[] addFive(int[] numbers) {
        return Arrays.stream(numbers).map(n -> n + 5).toArray();
    }

    public static int[] squareNumbers(int[] numbers) {
        return Arrays.stream(numbers).map(n -> n * n).toArray();
    }
}
