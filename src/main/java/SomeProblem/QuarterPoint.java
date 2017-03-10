package SomeProblem;

/**
 * Created by 77060 on 2017/3/8.
 */
public class QuarterPoint {
    public static void main(String[] args) {
        int[] a = new int[]{ 1, 1,0, 1, 1, 1, 1, 1};
        int[] b = new int[]{1,1,2,9,1,2,1,1,1,2,2,4,4};
        System.out.println(findQuarter(b));
    }

    public  static boolean findQuarter(int[] a) {

        if(a.length < 5) return false;
        int le1 = 1, le2 = le1 + 2, r1 = a.length - 2, r2 = r1 - 2;     // [0, le1), (le1, le2), (r2, r1), (r1, a.length) 为别为4个部分的和
        int sum1 = a[le1 - 1], sum2 = a[le2 - 1], sum3 = a[r2 + 1], sum4 = a[r1 + 1];
        // 不变式
        while(r1 - le1 >= 4) {
            if(sum1 < sum4) {
                sum1 += a[le1++];
                sum2 -= a[le1];
            } else if(sum1 > sum4){
                sum4 += a[r1--];
                sum3 -= a[r1];
            } else {
                if(le2 <= le1) {
                    le2 = le1 + 2;
                    sum2 = a[le2 - 1];
                }
                if(r2 >= r1) {
                    r2 = r1 - 2;
                    sum3 = a[r2 + 1];
                }
                while(le2 < r2 && (sum2 < sum1 || a[le2] == 0)) {
                    sum2 += a[le2++];
                }
                while(le2 < r2 && (sum3 < sum1 || a[r2] == 0)) {
                    sum3 += a[r2--];
                }
                if(le2 == r2 && sum1 == sum2 && sum2 == sum3) return true;
                sum1 += a[le1++];
                sum2 -= a[le1];
            }
        }
        return false;
    }
}
