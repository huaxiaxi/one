package start.A01_array;
import java.util.Scanner;

public class A001_binary_Search {
    public static void main(String[] args) {Scanner in = new Scanner(System.in);
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
        int index = function(arr, target);
        System.out.println(index);
    }

    private static int function(int[] arr, int target) {
        int len = arr.length;
        int left = 0;
        int right = len;
        int result = binary_search(arr,target,left,right);
//        int mid = left + (right - left) / 2;
//        if (arr[mid] == target){
//            return mid;
//        }else if (arr[mid] > target){
//            right = mid;
//        } else if (arr[mid] < target) {
//            left = mid;
//        } else if (left == mid && arr[mid] == target) {
//            return -1;
//        }

//        Arrays.stream(arr).forEach(System.out::println);
//        System.out.println(target);
        return result;
    }

    private static int binary_search(int[] arr, int target, int left, int right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target){
            return mid;
        }else if (left == mid && arr[mid] != target) {
            return -1;
        }
        if (arr[mid] > target){
            right = mid;
        } else {
            left = mid;
        }
        return binary_search(arr,target,left,right);
    }
}
