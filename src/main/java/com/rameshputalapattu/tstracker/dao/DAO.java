package com.rameshputalapattu.tstracker.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> list();
    void create(T t);

    Optional<T> get(int id);
    void update(int id,T t);
    void delete(int id);
}
