package start.A03_hashtable;

import java.util.*;

import static start.A03_hashtable.C002_intersection.getArray;

public class C004_two_sum {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            String scIn1 = sc.nextLine();
            Integer target = sc.nextInt();
            int[] arr1 = getArray(scIn1);
            if (arr1 == null) return;
            int[] result = getArrayIndicates(arr1, target);
            if (result == null) {
                System.out.println(-1);
                continue;
            }

            Arrays.stream(result).forEach(System.out::println);
        }


    }

    private static int[] getArrayIndicates(int[] arr, Integer target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])){
                result[0] = map.get(arr[i]);
                result[1] = i;
                return result;
            }
            map.put(target - arr[i], i);
        }

        return null;
    }



}
