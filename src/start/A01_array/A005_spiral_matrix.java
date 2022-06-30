package start.A01_array;
import java.util.Arrays;
import java.util.Scanner;

public class A005_spiral_matrix {
    public static void main(String[] args) {
        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println("请输入正整数：");
            int target = in.nextInt();
            int[][] index = function1( target);
            Arrays.stream(index).forEach(ind -> {
                System.out.println(Arrays.toString(ind));
            });

        }
    }
    //  上，右，下，左，采取，左开右闭区间[left, right) 循环过程
    private static int[][] function1(int target) {
        int[][] result = new int[target][target];
        int loop = 0;
        int start = 0;
        int value = 1;
        int i, j;

        while (loop++ < target / 2) {
//            模拟上
            for (j = start; j < target - loop; j++) {
                result[start][j] = value++;
            }
//            模拟右
            for ( i = start; i < target - loop; i++) {
                result[i][j] = value++;
            }
//            模拟下
            for (; j >= loop ; j--) {
                result[i][j] = value++;
            }
//            模拟左
            for (; i >= loop; i--) {
                result[i][j] = value++;
            }
            start++;
        }

        if (target % 2 == 1){
            result[start][start] = value;
        }
        
        return result;
    }

}
