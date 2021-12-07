import java.util.*;

public class leetcode891 {
    public static int rabbitsInForest(int[] arr){
        int res = 0;
        if (arr.length > 0){
            Map<Integer, Integer> map =  new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                if (map.containsKey(arr[i]) ){
                    if (map.get(arr[i]) == arr[i] + 1){
                        res += arr[i] + 1;
                        map.putIfAbsent(arr[i], 1);
                    }
                    else {
                        map.putIfAbsent(arr[i], map.get(arr[i]) + 1);
                    }
                }else {
                    map.put(arr[i], 1);
                }
            }
            for (Integer i : map.keySet()) {
                res += i + 1;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{10, 10, 2};
        System.out.println(rabbitsInForest(arr));
    }
}
