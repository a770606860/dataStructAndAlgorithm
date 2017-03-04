package select;

import sort.Sorter;

import java.util.Arrays;

/**
 * Created by 77060 on 2016/12/19.
 */
public class Selector {
    // 这个函数的排序方法并不太完美，不应该假设下标为L的元素在进入循环前等于pivot，这样会使得代码需要先确保位置L需要与某个值为pivot的位置交换值
    public static int selectXth(int[] list, int x) {
        if(x <= 0 || x > list.length) throw new IllegalArgumentException();
        x--;
        int s = 0, e = 0, L = 0, r = list.length;
        int pivot=list[L];
        // 不变式：循环开始时[0,s)中的元素小于pivot,[s,e]中的元素等于pivot，e < i
        // 如果在循环体某次执行后有x属于[s, e]则停止循环
        while(true) {
            for(int i = L + 1; i < r; i++) {
                if(list[i] < pivot) {
                    Sorter.exchange(list, e + 1, i);
                    Sorter.exchange(list, s++, ++e);
                } else if(list[i] == pivot) {
                    Sorter.exchange(list, ++e, i);
                }
            }
            // 维持不变式
            if(x > e) {
                L = e + 1;
            } else if(x < s) {
                r = s;
            } else return pivot;
            pivot = list[L];
            s = L;
            e = L;
        }
    }

    final static int PARTSIZE = 7;

    // 这里的th指定为下标
    private static int doSelectIth2(int[] list, int from, int to, int th) {
        if(to - from <= PARTSIZE) {
            Arrays.sort(list, from, to);
            return list[th];
        }
        int L = to - from;
        int size = L / PARTSIZE;
        if(L % PARTSIZE != 0) size++;
        int[] nList = new int[size];

        for(int i = 0; i < size - 1; i++) {
            int base = from + i * PARTSIZE;
            Arrays.sort(list, base, base + PARTSIZE);
            nList[i] = list[base + PARTSIZE / 2];
        }
        Arrays.sort(list,from + (size - 1) * PARTSIZE, to);
        nList[size - 1] = list[from + (size - 1) * PARTSIZE + (L % PARTSIZE) / 2];

        int mid = doSelectIth2(nList, 0, nList.length, nList.length / 2 - 1);
        int[] pos = partition(list, from, to, mid);

        // 这是尾递归，很容易可以转化为循环迭代
        if(pos[0] > th) {
            return doSelectIth2(list, from, L, th);
        } else if(pos[1] <= th) {
            return doSelectIth2(list, pos[1], to, th);
        } else
            return list[th];
    }

    // 根据pivot来划分数组，[L，r）中为pivot
    public static int[] partition(int[] list, int from, int to, int pivot) {
        int L = from, r = from;
        // 不变式[from, L)为小于pivot的元素，[L, r)为等于pivot的元素
        for(int i = from; i < to; i++) {
            if(list[i] == pivot) {
                Sorter.exchange(list, r++, i);
            } else if(list[i] < pivot) {
                Sorter.exchange(list, r, i);
                Sorter.exchange(list, r++, L++);
            }
        }
        return new int[]{L, r};
    }

    // 选择第 i 个元素，i 从 1 开始
    public static int selectIth2(int[] list, int from, int to, int th) {
        th--;
        if(from < 0 || from >= to || to > list.length) throw new IllegalArgumentException();
        if(th < from || th >= to) throw new IllegalArgumentException();
        // 首先拷贝一份副本
        int[] myList = Arrays.copyOfRange(list, from, to - from);
        return doSelectIth2(myList, 0, myList.length, th - from);
    }
}
