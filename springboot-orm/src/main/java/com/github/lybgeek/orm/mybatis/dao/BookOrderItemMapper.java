package com.github.lybgeek.orm.mybatis.dao;

import com.github.lybgeek.orm.mybatis.model.BookOrderItem;
import com.github.lybgeek.orm.mybatis.model.BookOrderItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookOrderItemMapper {
    long countByExample(BookOrderItemExample example);

    int deleteByExample(BookOrderItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookOrderItem record);

    int insertSelective(BookOrderItem record);

    List<BookOrderItem> selectByExample(BookOrderItemExample example);

    BookOrderItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookOrderItem record, @Param("example") BookOrderItemExample example);

    int updateByExample(@Param("record") BookOrderItem record, @Param("example") BookOrderItemExample example);

    int updateByPrimaryKeySelective(BookOrderItem record);

    int updateByPrimaryKey(BookOrderItem record);
}