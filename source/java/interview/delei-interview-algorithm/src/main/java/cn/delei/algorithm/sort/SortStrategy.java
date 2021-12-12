package cn.delei.algorithm.sort;

/**
 * 排序方法策略
 *
 * @author deleiguo
 */
public class SortStrategy {
    private IArraySort sort;

    public SortStrategy(IArraySort sort) {
        this.sort = sort;
    }

    public int[] sort(int[] data) {
        return this.sort.sort(data);
    }
}
