package others.commonproblems;

import java.util.Arrays;

/**
 * Created by 77060 on 2016/12/21.
 */
public class LongestSubSeq {
    /* C[i][j]记录 listA[i - 1] 与 listB[j - 1]的最长公共子序列长度，则有如下关系
     *           / 0                    如果 i = 0 || j = 0
     * C[i][j] =   C[i - 1][j - 1] + 1  如果 listA[i] == listB[j]
     *          \ max{C[i][j - 1], C[i - 1][j]}     如果listA[i] != listB[j]
     */
    public static int longestLength(int[] listA, int[] listB) {
        int[][] C = new int[listA.length + 1][listB.length + 1];

        Arrays.fill(C[0], 0);
        for(int i = 0; i < listA.length + 1; i++)
            C[i][listB.length] = 0;

        // 根据状态转移方程可以知道在求 C[i][j] 时需要先求出 C[i - 1][j - 1], C[i][j - 1] 以及 C[i - 1][j]
        // 所以循环应该从上往下，从左往右一次遍历
        for(int i = 1; i < listA.length + 1; i++) {
            for(int j = 1; j < listB.length + 1; j++) {
                if(listA[i - 1] == listB[j - 1])
                    C[i][j] = C[i - 1][j - 1] + 1;
                else C[i][j] = Math.max(C[i - 1][j], C[i][j - 1]);
            }
        }

        return C[listA.length][listB.length];
    }
}
