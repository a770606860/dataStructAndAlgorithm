package others.commonproblems;

/**
 * Created by 77060 on 2016/12/21.
 */
public class OptimalBinaryTree {

    /*  costA[i] 记录关键字 ai 的出现频率，costB[i] 记录叶节点 bi 的出现频率（叶节点 bi 是所有在 ai-1 到 ai 范围内的关键字，这些关键字没有对应映射）
     *  T[i] = costA[0..i] + costB[0..(i + 1)]（costX[0..i]表示0到i的cost加和）
     *  h(i, j) = T[j] - T[i] + costA[i] + costB[i] + costB[i + 1]
     *  令 S[i, j] 表示关键字 ai 到 aj 以及叶节点 bi 到 b(j + 1) 的最优二叉树代价和，则有如下状态转移方程：
     *            / costB[j]     i = j + 1
     *  S[i, j] = \ min{S[i, k - 1], S[k + 1, j] + h(i, j)} (i <= k <= j)   i <= j
     */

    /**
     * 这个代码中最值得学习的是下标转换的思想，领悟出这一点花5个小时。原来是那么简单。。
     */
    public static int findOptimalCost(int[] costA, int[] costB) {
        if(costA.length == 0) return 0;
        int la = costA.length;
        int[] T = new int[la];
        T[0] = costA[0] + costB[0] + costB[1];
        for(int i = 1; i < la; i++) {
            T[i] = T[i - 1] + costA[i] + costB[i + 1];
        }
        int[][] S = new int[la + 1][la + 1];
        for(int j = -1; j < la; j++) {
            S[j + 1][cIndex(j)] = costB[j + 1];
        }

        for(int L = 0; L < la; L++) {
            for(int i = 0; i + L < la; i++) {
                int min = Integer.MAX_VALUE;
                int c = T[i + L] - T[i] + costA[i] + costB[i] + costB[i + 1];
                for(int k = i; k <= i + L; k++) {
                    min = Math.min(min, S[i][cIndex(k - 1)] + S[k + 1][cIndex(i + L)]);
                }
                S[i][cIndex(i + L)] = min + c;
            }
        }

        return S[0][cIndex(la - 1)];
    }

    private static int cIndex(int x) {
        return x + 1;
    }
}
