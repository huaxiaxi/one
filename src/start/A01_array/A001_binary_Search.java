package start.A01_array;
import java.util.Scanner;

public class A001_binary_Search {
    public static void main(String[] args) {
        extracted();
    }

    static void extracted() {
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
        int index = function(arr, target);
        System.out.println(index);
    }

    //    左闭右闭[left, right]
    private static int function1(int[] arr, int target){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > target) {
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    //    左闭右开[left, right)
    private static int function2(int[] arr, int target){
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > target) {
                right = mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    private static int function(int[] arr, int target) {
        int len = arr.length;
        int left = 0;
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
        return binary_search(arr,target,left, len);
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
