package start.A04_string;

import java.util.Map;
import java.util.Scanner;

public class D002_reverse_string_II {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            String scIn1 = sc.nextLine();
            int k = sc.nextInt();
            if (scIn1.isBlank()){
                System.out.println(-1);
                continue;
            }
            char[] s = scIn1.toCharArray();
//            reverseII1(s, k);
            reverseII2(s, k);
            for (int i = 0; i < s.length; i++) {
                System.out.print(s[i] + " -> ");
            }
        }
    }


    private static void reverseII2(char[] s, int k) {
        int right;
        for (int i = 0; i < s.length; i += 2 * k) {
             right = Math.min(s.length - 1, i + k - 1);
             reverse(s, i, right);
        }
    }
    private static void reverseII1(char[] s, int k) {
        int i ;
        int j ;
        int count = 0;

        for (int l = 0; l < s.length; l++) {
            if (l >= k && l % (count * k + k) == 0) {
                i = count * k;
                j = (count + 1) * k;
                reverse(s, i, j - 1);
                count += 2;
            }
        }
        if (s.length - k * count > 0 && s.length - k * count <= k){
            System.out.println("超了点！！！！");
            reverse(s, count * k, s.length - 1);
        }
    }
    private static void reverse(char[] s,  int left, int right) {
        while (right > left) {
            s[left] ^= s[right];
            s[right] ^= s[left];
            s[left] ^= s[right];
            left++;
            right--;
        }
    }


}
