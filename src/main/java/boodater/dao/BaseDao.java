package boodater.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<K extends Serializable, D> {
    List<D> selectAll();
}
