package start.A03_hashtable;

import java.util.*;

import static start.A03_hashtable.C002_intersection.getArray;

public class C007_three_sum {
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

        if (arr.length < 3 || arr[0] * arr[arr.length - 1] > 0){
            return result;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                return result;
            }

            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = arr.length - 1;
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(arr[i], arr[left], arr[right]));
                    while (left < right && arr[right] == arr[right - 1]) {
                        right--;
                    }
                    while (left < right && arr[left] == arr[left + 1]) {
                        left++;
                    }

                    right--;
                    left++;

                }
            }

        }


        return result;
    }


}

