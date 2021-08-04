package cn.delei.spring.boot.transaction.infrastructure;

public interface TxBaseService<T extends BaseEntiry> {
    void add(T t);

    void update(T t);

    T selectByPrimaryKey(Long key);

    void deleteByPrimaryKey(Long key);
}
