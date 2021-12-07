public class leetcode1652 {
    public static int[] dismantleBoom(int[] arr, int k){
        int l = arr.length;
        int[] res = new int[l];
        int x = 0, y = 0, tmp = 0;
        if (k > 0){
            for (int i = 0; i < l; i++) {
                if (i == 0){
                    for (int j = 1; j < k + 1; j++) {
                        tmp += arr[(j % l)];
                    }
                    x = 1;
                    y = k % l;
                }else {
                    y = (y + 1) % l;
                    tmp += arr[y] - arr[x];
                    x = (x + 1) % l;
                }
                res[i] = tmp;
            }
        }else if (k < 0){
            int k2 = Math.abs(k);
            for (int i = 0; i < l; i++) {
                if (i == 0){
                    for (int j = l - k2; j < l; j++) {
                        tmp += arr[(j % l)];
                    }
                    x = (l - k2) % l;
                    y = l - 1;
                }else {
                    y = (y + 1) % l;
                    tmp += arr[y] - arr[x];
                    x = (x + 1) % l;
                }
                res[i] = tmp;
            }
        }else {
            for (int i = 0; i < l; i++) {
                res[i] = 0;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 4, 9, 3};
        int k = -2;
        int[] res = dismantleBoom(arr, k);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }

}
