package searchtree;

/**
 * Created by 77060 on 2017/2/18.
 * 这个searchTree的所有实现不能有重复关键字
 */
public interface SearchTree<T> {
    T search(T t);
    T minimum();
    T maximum();

    /**
     * 寻找搜索树中小于 t 且最靠近 t 的值
     * @param t
     * @return
     */
    T predecessor(T t);

    /**
     * 寻找搜索树中 大于 t 且最靠近 t 的值
     * @param t
     * @return
     */
    T successor(T t);
    void insert(T t);
    void delete(T t);
    int size();
    boolean isEmpty();
}
