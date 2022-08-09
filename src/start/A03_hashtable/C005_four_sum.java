package start.A03_hashtable;

import java.util.*;

import static start.A03_hashtable.C002_intersection.getArray;

public class C005_four_sum {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            String scIn1 = sc.nextLine();
            String scIn2 = sc.nextLine();
            String scIn3 = sc.nextLine();
            String scIn4 = sc.nextLine();
//            Integer target = sc.nextInt();
            int[] arr1 = getArray(scIn1);
            int[] arr2 = getArray(scIn2);
            int[] arr3 = getArray(scIn3);
            int[] arr4 = getArray(scIn4);
            if (arr1 == null || arr2 == null || arr3 == null ||arr4 == null) {
                System.out.println(-1);
                continue;
            }

            int result = getFourSum(arr1, arr2, arr3, arr4);

            System.out.println(result);
        }


    }

    private static int getFourSum(int[] arr1, int[] arr2, int[] arr3, int[] arr4) {
        int N = arr1.length;
        int result = 0;
        Map<Integer, Integer> mapL = new HashMap<>();
        Map<Integer, Integer> mapR = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int temp1 = arr1[i] + arr2[j];
                if (mapL.containsKey(temp1)){
                    int value1 = mapL.get(temp1) + 1;
                    mapL.put(temp1, value1);
                }
                mapL.put(temp1, 1);
                int temp2 = arr3[i] + arr4[j];
                if (mapR.containsKey(temp2)){
                    int value2 = mapL.get(temp2) + 1;
                    mapR.put(temp2, value2);
                }
                mapR.put(temp2, 1);
            }
        }
        for (int key: mapL.keySet()) {
            if (mapR.containsKey(-key)){
                result += mapL.get(key) * mapR.get(-key);
            }
        }
        return result;
    }




}
