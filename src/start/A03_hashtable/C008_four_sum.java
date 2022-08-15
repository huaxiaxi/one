package start.A03_hashtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static start.A03_hashtable.C002_intersection.getArray;

public class C008_four_sum {
    public static void main (String[]args){
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            String scIn1 = sc.nextLine();
            Integer target = sc.nextInt();
            int[] arr1 = getArray(scIn1);
            if (arr1 == null) return;
            List<List<Integer>> result = getArrayIndicates(arr1, target);
            if (result == null || result.size() < 1) {
                System.out.println(-1);
                continue;
            }
            result.forEach(s -> {
                s.forEach(System.out::println);
                System.out.println("----<>----");
            });

        }


    }

    private static List<List<Integer>> getArrayIndicates (int[] arr, Integer target){
        List<List<Integer>> result = new ArrayList<>() ;
        Arrays.parallelSort(arr);

        if (arr.length < 4){
            return result;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0 && arr[i] > target) {
                return result;
            }

            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }

            for (int j = i + 1; j < arr.length; j++) {
                if (j > i + 1 && arr[j - 1] == arr[j]) {
                    continue;
                }
                int left = j + 1;
                int right = arr.length - 1;

                while (left < right) {
                    long sum = (long) arr[i] + arr[j] + arr[left] + arr[right];
                    if (sum > target) {
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        result.add(Arrays.asList(arr[i], arr[j], arr[left], arr[right]));
                        while (right > left && arr[right] == arr[right - 1]) right--;
                        while (right > left && arr[left] == arr[left + 1]) left++;

                        right--;
                        left++;
                    }
                }

            }
        }


        return result;
    }


}

