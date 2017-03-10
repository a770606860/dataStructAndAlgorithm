package tools;

import java.util.Comparator;

/**
 * Created by 77060 on 2017/3/4.
 */
public class ArrayTools {

    public static <T extends Comparable<T>> int binarySearchFirstOccur(T[] list, T t) {
        return binarySearchFirstOccur(list, 0, list.length, t);
    }

    /**
     * 使用前提：list 从小到大排序，否则结果未定义
     * 在 list 的 [from, to) 范围中查找 t 第一次出现的下标
     * 如果 t 不在 [from, to) 中，则返回(-(insertion point) - 1)
     */
    public static <T extends Comparable<T>> int binarySearchFirstOccur(T[] list, int from, int to, T t) {
        int lt = from, r = to;
        // 不变式，[from, lt) < t, [r, 0-0) >= t
        while(lt < r) {
            int mid = (lt + r) / 2;
            int x = list[mid].compareTo(t);
            if(x < 0) lt = mid + 1;
            else r = mid;
        }
        return r < to && t.compareTo(list[r]) == 0 ? r : -r - 1;
    }

    public static <T extends Comparable<T>> int binarySearchFirstOccur(T[] list, T t, Comparator<T> comparator) {
        return binarySearchFirstOccur(list, 0, list.length, t, comparator);
    }

    /**
     * 使用前提：list 从小到大排序，否则结果未定义
     * 在 list 的 [from, to) 范围中查找 t 第一次出现的下标
     * 如果 t 不在 [from, to) 中，则返回(-(insertion point) - 1)
     */
    public static  <T> int binarySearchFirstOccur(T[] list, int from, int to, T t, Comparator<T> comparator) {
        int lt = from, r = to;
        while(lt < r) {
            int mid = (lt + r) / 2;
            int x = comparator.compare(list[mid], t);
            if(x < 0) lt = mid + 1;
            else r = mid;
        }
        return r < to && comparator.compare(t, list[r]) == 0 ? r : -r - 1;
    }

    public static <T extends Comparable<T>> int binarySearchLastOccur(T[] list, T t) {
        return binarySearchLastOccur(list, 0, list.length, t);
    }

    /**
     * 使用前提：list 从小到大排序，否则结果未定义
     * 在 list 的 [from, to) 范围中查找 t 最后一次出现的下标
     * 如果 t 不在 [from, to) 中，则返回(-(insertion point) - 1)
     */
    public static <T extends Comparable<T>> int binarySearchLastOccur(T[] list, int from, int to, T t) {
        int lt = from, r =to;

        // 不变式：[from, lt) <= t, [r, 0-0) > t
        while(lt < r) {
            int mid = (lt + r) / 2;
            if(list[mid].compareTo(t) <= 0) lt = mid + 1;
            else r = mid;
        }
        return r > from && t.compareTo(list[r - 1]) == 0 ? r - 1 : -r - 1;
    }

    public static <T> int binarySearchLastOccur(T[] list, T t, Comparator<T> comparator) {
        return binarySearchLastOccur(list, 0, list.length, t, comparator);
    }

    /**
     * 使用前提：list 从小到大排序，否则结果未定义
     * 在 list 的 [from, to) 范围中查找 t 最后一次出现的下标
     * 如果 t 不在 [from, to) 中，则返回(-(insertion point) - 1)
     */
    public static <T> int binarySearchLastOccur(T[] list, int from, int to, T t, Comparator<T> comparator) {
        int lt = from, r = to;

        while(lt < r) {
            int mid = (lt + r) / 2;
            if(comparator.compare(list[mid], t) <= 0) lt = mid + 1;
            else r = mid;
        }
        return r > from && comparator.compare(t, list[r - 1]) == 0 ? r - 1 : -r - 1;
    }

}
