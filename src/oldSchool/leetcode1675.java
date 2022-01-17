package oldSchool;

import java.util.TreeSet;

public class leetcode1675 {
    public static int minimumDeviation(int[] arr){
        TreeSet<Integer> ts = new TreeSet<>();
        for (Integer i : arr) {
            ts.add(i % 2 == 0 ? i : i * 2);
        }
        int res = ts.last() - ts.first();
        while (res > 0 && ts.last() % 2 == 0){
            int max = ts.last();
            ts.remove(max);
            ts.add(max / 2);
            res = Math.min(res, ts.last() - ts.first());
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 1, 5, 20, 3};
//        int[] arr = new int[]{4, 7};
        System.out.println(minimumDeviation(arr));
    }
}
