package webapp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{2, 4, 3, 1, 3, 2}));
        System.out.println(oddOrEven(Arrays.asList(1, 3, 5, 3, 4)));
    }

    private static int minValue(int[] values) {
        AtomicInteger result = new AtomicInteger(0);
        AtomicInteger count = new AtomicInteger(1);
        Arrays.stream(values)
                .boxed()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .forEach((d) -> {
                    result.set(d * count.get() + result.get());
                    count.set(count.get() * 10);
                });
        return result.get();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers
                .stream()
                .reduce(0, Integer::sum);
        return integers
                .stream()
                .filter((d) -> (sum % 2 != 0) == (d % 2 == 0))
                .collect(Collectors.toList());
    }
}
