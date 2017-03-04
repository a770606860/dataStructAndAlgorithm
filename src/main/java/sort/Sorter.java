package sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 77060 on 2016/12/13.
 */
public class Sorter {

    public static void mergeSort(int[] list, int from, int to) {
        assert to <= list.length;
        assert from >= 0;
        assert from < to;
        doMergeSort(list, from, to);
    }

    // 稳定归并排序，但是不具有原址性
    private static void doMergeSort(int[] list, int from, int to) {
        if(from >= to - 1) return;
        int mid = (from + to) / 2;
        int[] listLeft = Arrays.copyOfRange(list, from, mid);
        doMergeSort(listLeft, 0, listLeft.length);
        int[] listRight = Arrays.copyOfRange(list, mid, to);
        doMergeSort(listRight, 0, listRight.length);

        // 不变式：[0, k)中包含的元素为list中前k小的元素，且list[k - 1] <= listLeft[i] && list[k - 1] <= listRight[j]
        int k = from, i = 0, j = 0;
        for(; i < listLeft.length && j < listRight.length; k++) {
            // 维持不变式
            if(listLeft[i] <= listRight[j]) {
                list[k] = listLeft[i++];
            } else {
                list[k] = listRight[j++];
            }
        }
        while(i < listLeft.length) list[k++] = listLeft[i++];
        while(j < listRight.length) list[k++] = listRight[j++];
    }

    // 这个排序不具有稳定性，对int的排序不需要稳定性
    public static void countSort(int[] list, int max) {
        int[] counters = new int[max];
        Arrays.fill(counters, 0);
        for(int e : list) {
            counters[e - 1]++;
        }

        int pos = 0;
        for(int i = 0; i < counters.length; i++) {
            if(counters[i] == 0) continue;
            else {
                while(counters[i]-- > 0) {
                    list[pos++] = i + 1;
                }
            }
        }

    }

    public static void countSort(List<Integer> list, int max) {
        int[] counters = new int[max];
        Arrays.fill(counters, 0);
        for(Integer e : list) {
            counters[e - 1]++;
        }
        for(int i = 0; i < counters.length - 1; i++) {
            counters[i + 1] += counters[i];
        }
        // 原址和迭代器中选一个折中，我选迭代器
        List<Integer> list1 = new ArrayList<>(list);

        for(int i = list1.size() - 1; i >= 0; i--) {
            list.set(--counters[list1.get(i) - 1], list1.get(i));
        }
    }

    // 将数组排序
    public static <T> void heapSort(T[] list) {
        // 建堆
        for(int i = list.length / 2 - 1; i >= 0; i--) {
            maintainHeap(list, i, list.length);
        }
        // 排序
        for(int i = 0; i < list.length - 1; i++) {
            exchange(list, 0, list.length - 1 - i);
            maintainHeap(list, 0, list.length -1 - i);
        }
    }

    // 维持堆性质
    private static <T> void maintainHeap(T[] list, int from, int to) {
        assert from <  list.length;
        assert to <= list.length;
        assert list[from] instanceof Comparable;
        Comparable[] listC = (Comparable[]) list;
        int cur = from;
        /*不变式：
          cur指示待整理的位置，from =< cur <= list.length
         */
        while(cur < to) {
            int left = (cur + 1) * 2 - 1;
            int right = (cur + 1) * 2;
            int max = cur;
            if(left < to && listC[left].compareTo(listC[max]) > 0) max = left;
            if(right < to && listC[right].compareTo(listC[max]) > 0) max = right;
            if(max == cur) break;
            exchange(list, max, cur);
            cur = max;
        }
    }

    // 快速排序，递归的方式
    // 排序范围[from, to)
    public static void quickSort(int[] list, int from, int to) {
        assert from >= 0;
        assert from < to;
        assert to <= list.length;
        doQuickSort(list, from, to);
    }

    private static void doQuickSort(int[] list, int from, int to) {
        if(from >= to - 1) return;
        int pivot = from;   // 改变这个pivot可以更改主元的选取策略（比如使用随机化算法）
        int[] bounds = partition(list, from, to, pivot);
        doQuickSort(list, from, bounds[0]);
        doQuickSort(list, bounds[1], to);
    }

    // 返回结果为int[] r，[r[0], r[1])中的元素等于list[pivot]
    public static int[] partition(int[] list, int from, int to, int pivot) {
        int pivotV = list[pivot];
        exchange(list, from, pivot);
        int less = from - 1, equal = from;
        for(int i = from + 1; i < to; i++) {
            if(list[i] == pivotV) {
                exchange(list, ++equal, i);
            } else if(list[i] > pivotV) {
                continue;
            } else {
                exchange(list,equal + 1, i);
                exchange(list, ++less, ++equal);
            }
        }
        return new int[]{less + 1, equal + 1};
    }

    public static <T> void exchange(T[] list, int max, int cur) {
        if(max == cur) return;
        T x = list[max];
        list[max] = list[cur];
        list[cur] = x;
    }

    public static void exchange(int[] list, int left, int r) {
        int x = list[left];
        list[left] = list[r];
        list[r] = x;
    }
}
