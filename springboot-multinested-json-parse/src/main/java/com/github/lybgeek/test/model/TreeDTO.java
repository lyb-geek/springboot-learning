package com.github.lybgeek.test.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
public class TreeDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 父id,根节点为0
     */
    private Integer parentId;

    private Integer sort;
    /**
     * 子菜单
     */
    private List<T> children = new ArrayList<T>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TreeDTO)) {
            return false;
        }
        TreeDTO that = (TreeDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}