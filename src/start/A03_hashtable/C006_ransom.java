package start.A03_hashtable;

import java.util.*;

import static start.A03_hashtable.C002_intersection.getArray;

public class C006_ransom {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入：");
            String ransom = sc.nextLine();
            String magazine = sc.nextLine();
//            Integer target = sc.nextInt();
            Boolean result = conConstruct(ransom, magazine);
            if (ransom.isBlank() || magazine.isBlank()){
                System.out.println(-1);
                continue;
            }
            System.out.println("is ransom? -->" + result);

        }


    }
//  在本题的情况下，使用map的空间消耗要比数组大一些的，因为map要维护红黑树或者哈希表，而且还要做哈希函数，是费时的！数据量大的话就能体现出来差别了。 所以数组更加简单直接有效！
    private static Boolean conConstruct(String text, String dict) {
        Set<Character> set = new HashSet<>();
        char[] t = dict.toCharArray();
        for (char c : t) {
            set.add(c);
        }
        char[] k = text.toCharArray();
        for (char c : k) {
            if (!set.contains(c)) {
                return false;
                }
            }

        return true;
    }


}
