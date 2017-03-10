package others.commonproblems;

import java.util.Arrays;

/**
 * Created by 77060 on 2016/12/17.
 */
public class DeleteAllInList {
    public static int[] deleteAllFromListAndResize(int[] list, int target) {
        int n = 0;
        for(int i = 0; i < list.length; i++) {
            if(list[i] != target) {
                list[n++] = list[i];
            }
        }

        int[] result = Arrays.copyOf(list, n);
        return result;
    };

    public static int[] deleteAllFromListAndRearrange(int[] list, int target) {
        int j = list.length;
        // 不变式：i之前的元素均为非target元素，j指向数组的末尾元素加一
        for(int i = 0; i < j; ) {
            while(i < j && list[i] != target) i++;
            while(i < j && list[j - 1] == target) j--;
            // 维持不变式
            if(i < j) {
                list[i++] = list[--j];
            }
        }

        int[] result = Arrays.copyOf(list, j);
        return result;
    }
}
