package oldSchool;

public class leetcode1694 {
    public static String resetPhoneNumbers(String s){
        String res = "";
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ' && s.charAt(i) != '-'){
                res += s.charAt(i);
                count ++;
                if (count % 3 == 0){
                    res += '-';
                    count ++;
                }
            }
        }
        if (res.length() < 3) return res;
        System.out.println(res.lastIndexOf('-'));
        if (res.lastIndexOf('-') == count - 1){
            System.out.println("her!");
            return res.substring(0, count - 2);
        }else if (res.lastIndexOf('-') == count - 2){
            System.out.println("hir!");
            String la = res.charAt(count - 1) + "" + res.charAt(count - 3);
            return res.substring(0, count - 3).concat(la);
        }else
        return res;
    }

    public static void main(String[] args) {
        String s = "1-2 3-45-6";
        System.out.println(resetPhoneNumbers(s));
    }
}
