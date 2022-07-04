package start.A01_array;
import java.util.Arrays;
import java.util.Scanner;

public class A003_array_element_sqrt {
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

            int[] index = function2(arr);
            Arrays.stream(index).forEach(System.out::println);
        }
    }
    //  双指针法1
    private static int[] function1(int[] arr) {
        int[] result = new int[arr.length];
        int leftIndex = 0;
        int rightIndex = arr.length - 1;
        if (arr[leftIndex] >= 0) {
            for ( ; leftIndex < arr.length; leftIndex++) {
                result[leftIndex] = arr[leftIndex] * arr[leftIndex];
            }
        } else if (arr[rightIndex] <= 0) {
            for (; rightIndex >= 0; rightIndex--) {
                result[leftIndex++] = arr[rightIndex] * arr[rightIndex];
            }
        } else {
            while (arr[leftIndex] < 0){
                leftIndex++;
            }
            while (arr[rightIndex] >= 0){
                rightIndex--;
            }
            int index = 0;
//                此时 arr.left >= 0, arr.right < 0, right < left
            while (rightIndex >= 0 && leftIndex < arr.length){
                if (Math.abs(arr[leftIndex]) <= Math.abs(arr[rightIndex])){
                    result[index++] = arr[leftIndex] * arr[leftIndex];
                    leftIndex++;
                }else {
                    result[index++] = arr[rightIndex] * arr[rightIndex];
                    rightIndex--;
                }
            }
            while (rightIndex >= 0){
                result[index++] = arr[rightIndex] * arr[rightIndex];
                rightIndex--;
            }
            while (leftIndex < arr.length){
                result[index++] = arr[leftIndex] * arr[leftIndex];
                leftIndex++;
            }
        }
        return result;
    }
    //  双指针法2，
    private static int[] function2(int[] arr) {
        int[] result = new int[arr.length];
        int leftIndex = 0;
        int rightIndex = arr.length - 1;
        int index = arr.length - 1;

        while (leftIndex <= rightIndex) {
            if (arr[leftIndex] * arr[leftIndex] <= arr[rightIndex] *  arr[rightIndex]) {
                result[index--] = arr[rightIndex] *  arr[rightIndex];
                rightIndex--;
            } else {
                result[index--] = arr[leftIndex] *  arr[leftIndex];
                leftIndex++;
            }
        }
        return result;
    }

}
