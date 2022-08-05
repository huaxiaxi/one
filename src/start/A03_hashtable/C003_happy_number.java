package start.A03_hashtable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class C003_happy_number {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            int target = sc.nextInt();

            Boolean result = isHappy(target);
            System.out.println("this number is happy --> " + result);

        }


    }

    private static Boolean isHappy(int num) {
        Set<Integer> set = new HashSet<>();

        int start = num;

        while (start != 1 && !set.contains(start)) {
            set.add(start);
            start =  getTotalNum(start);
        }
//        set.stream().forEach(System.out::println);

        return start==1;

    }

    private static int getTotalNum(int start) {
        int total = 0;
        while (start != 0) {
            int temp = start % 10;
            total += temp * temp ;
            start = start / 10;
        }
        return total;
    }


}
