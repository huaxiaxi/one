package start.A03_hashtable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class C002_intersection {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            String scIn1 = sc.nextLine();
            String scIn2 = sc.nextLine();
//            Integer target = sc.nextInt();
            int[] arr1 = getArray(scIn1);
            int[] arr2 = getArray(scIn2);
            if (arr1 == null || arr2 == null) return;

            int[] result = getArrayIntersection(arr1, arr2);

            Arrays.stream(result).forEach(System.out::println);

        }


    }

    private static int[] getArrayIntersection(int[] arr1, int[] arr2) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();

        for (int i : arr1) {
            set1.add(i);
        }

        for (int i : arr2) {
            if (set1.contains(i)) {
                set2.add(i);
            }
        }

        return set2.stream().mapToInt(x -> x).toArray();
    }

    public static int[] getArray(String scIn1) {
        if (scIn1.length() == 0 || scIn1.isBlank()) {
            System.out.println(-1);
            return null;
        }
        String[] arrStr = scIn1.split(" ");
        int[] arr = new int[arrStr.length];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(arrStr[i]);
        }
        return arr;
    }

}
