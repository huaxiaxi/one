package start.A04_string;

import java.util.List;
import java.util.Scanner;

import static start.A03_hashtable.C002_intersection.getArray;

public class D001_reverse_string {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            String scIn1 = sc.nextLine();
            if (scIn1.isBlank()){
                System.out.println(-1);
                continue;
            }
            char[] s = scIn1.toCharArray();
            char[] result = reverse(s);
            reverse2(s);
            for (int i = 0; i < result.length; i++) {
                System.out.print(s[i] + " -> ");
            }
        }
    }

    private static void reverse2(char[] s) {
        int i = 0;
        int j = s.length - 1;
        while (j > i) {
            s[i] ^= s[j];
            s[j] ^= s[i];
            s[i] ^= s[j];
            i++;
            j--;
        }
    }

    private static char[] reverse(char[] s) {
        int i = 0;
        int j = s.length - 1;
        while (j > i) {
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            j--;
            i++;
        }


        return s;
    }
}
