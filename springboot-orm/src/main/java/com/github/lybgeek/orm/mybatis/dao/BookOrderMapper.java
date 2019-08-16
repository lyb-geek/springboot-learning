package com.github.lybgeek.orm.mybatis.dao;

import com.github.lybgeek.orm.mybatis.model.BookOrder;
import com.github.lybgeek.orm.mybatis.model.BookOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookOrderMapper {
    long countByExample(BookOrderExample example);

    int deleteByExample(BookOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookOrder record);

    int insertSelective(BookOrder record);

    List<BookOrder> selectByExample(BookOrderExample example);

    BookOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookOrder record, @Param("example") BookOrderExample example);

    int updateByExample(@Param("record") BookOrder record, @Param("example") BookOrderExample example);

    int updateByPrimaryKeySelective(BookOrder record);

    int updateByPrimaryKey(BookOrder record);
}