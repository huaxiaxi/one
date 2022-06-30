package start.A01_array;
import java.util.Scanner;

public class A004_min_length_sub_array {
    public static void main(String[] args) {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("请输入数组：");
            String inStr = in.nextLine();
            if (inStr.length()==0 || inStr.isBlank()){
                System.out.println(-1);
                return;
            }
            String[] arrStr= inStr.split(" ");
            int[] arr = new int[arrStr.length];

            for(int i = 0; i< arr.length; i++) {
                arr[i] = Integer.parseInt(arrStr[i]);
            }
            int target = in.nextInt();
            int index = function1(arr, target);
            System.out.println(index);
        }
    }
    //  双指针法1，同向移动 位置移动len-num(target)次
    private static int function1(int[] arr, int target) {
        int leftIndex = 0;
        int rightIndex = 0;
        int len = Integer.MAX_VALUE;
        int sum = 0;

        while (rightIndex < arr.length) {
            sum = sum + arr[rightIndex];
            while (leftIndex <= rightIndex && sum >= target){
                len = Math.min(len, rightIndex - leftIndex + 1);
                sum = sum - arr[leftIndex];
                leftIndex++;
            }
            rightIndex++;
        }
        if (len == Integer.MAX_VALUE){
            return 0;
        }
        return len;
    }

}
