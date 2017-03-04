package disjointSet;

import java.util.List;

/**
 * Created by 77060 on 2017/2/17.
 */
public interface DisjointSet<T> {
    /**
     * 创建一个集合，其中包含唯一元素t
     */
    void put(T t);

    /**
     * 合并 t1 与 t2 所在集合
     */
    void union(T t1, T t2);

    /**
     * 判断 t1 与 t2 是否在同一个集合内
     * @return 真，在同一集合；假，不在同一集合
     */
    boolean isTogether(T t1, T t2);

    /**
     * 返回代表该集合的元素
     */
    T findSet(T t);

    /**
     * 获取 t 所在集合的所有元素
     */
    List<T> setOf(T t);
}
