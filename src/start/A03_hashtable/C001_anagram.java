package start.A03_hashtable;

import java.util.Scanner;

public class C001_anagram {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String s = sc.nextLine();
            String e = sc.nextLine();
            if (s.isBlank() || e.isBlank()) {
                return ;
            }
            Boolean result = isAnagram(s, e);
            System.out.println("Two word is ANAGRAM? ->" + result);
        }


    }

    private static Boolean isAnagram(String s, String e) {
        if (s.length() == e.length()) {
            int[] record = new int[26];
            for (int i = 0; i < s.length(); i++) {
                record[s.charAt(i) - 'a']++;
            }
            for (int i = 0; i < e.length(); i++) {
                record[e.charAt(i) - 'a']--;
            }
            for (int i = 0; i < record.length; i++) {
                if (record[i] != 0) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
}
