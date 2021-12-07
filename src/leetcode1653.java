public class leetcode1653 {
    public static int minDelOperate(String s){
        int cb = 0;
        int ca = 0;
        for (Character c:s.toCharArray()) {
            if (c == 'a'){
                if (cb != ca){
                    cb++;
                }
            }else {
                ca++;
            }
        }
        return cb;
    }

    public static void main(String[] args) {
        String s = "aaaabbabbabbbbabbbb";
        System.out.println(minDelOperate(s));
    }
}
