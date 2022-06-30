package start.A01_array;
import java.util.Scanner;

import static start.A01_array.A001_binary_Search.extracted;

public class A002_remove_element {
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
            int index = function2(arr, target);
            System.out.println(index);
        }
    }
    //  双指针法1，同向移动 位置移动len-num(target)次
    private static int function1(int[] arr, int target) {
        int slowIndex = 0;
        for (int fastIndex = 0; fastIndex < arr.length; fastIndex++) {
            if (arr[fastIndex] != target) {
                arr[slowIndex++] = arr[fastIndex];
            }
        }
        return slowIndex;
    }
    //  双指针法2，相向移动 移动<=num(target)次 与right位置有关
    private static int function2(int[] arr, int target) {
        int leftIndex = 0;
        int rightIndex = arr.length - 1;
        while (leftIndex <= rightIndex) {
            while (leftIndex <= rightIndex && arr[leftIndex] != target){
                leftIndex++;
            }
            while (leftIndex <= rightIndex && arr[rightIndex] == target){
                rightIndex--;
            }
            if (leftIndex < rightIndex){
                arr[leftIndex++] = arr[rightIndex--];
            }

        }

        return leftIndex;
    }

}
