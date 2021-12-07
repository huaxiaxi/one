import java.util.Stack;

public class leetcode1673 {
    public static int[] mostPowerSubConsequence(int[] arr, int k){
        int[] res = new int[k];
        int n = arr.length;
        Stack<Integer> sta = new Stack<>();
       // sta.push(-1);
        for (int i = 0; i < n; i++) {
            while (!sta.empty() && sta.size() + n - i > k && arr[i] < sta.peek()){
                sta.pop();
            }
            if (sta.size() < k){
                sta.push(arr[i]);
            }
        }
        for (int i = k; i > 0; i--) {
            res[i - 1] = sta.pop();
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 4, 3, 3, 5, 4, 9, 6};
        int k = 4;
        int[] res = mostPowerSubConsequence(arr, k);

        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }
}
