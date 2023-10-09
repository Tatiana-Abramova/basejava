package webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{2, 4, 3, 1, 3, 2}));
        System.out.println(oddOrEven(Arrays.asList(1, 3, 5, 3, 4)));
        System.out.println(oddOrEven2(Arrays.asList(1, 3, 5, 3, 4)));

    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
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

    private static List<Integer> oddOrEven2(List<Integer> integers) {
        Map<Boolean, List<Integer>> groups = integers
                .stream()
                .collect(Collectors.partitioningBy((d) -> d % 2 != 0));
        return groups.get(true).size() % 2 != 0
                ? groups.get(false)
                : groups.get(true);
    }
}
