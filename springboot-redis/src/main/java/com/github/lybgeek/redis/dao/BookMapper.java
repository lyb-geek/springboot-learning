package com.github.lybgeek.redis.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.lybgeek.redis.model.Book;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lyb-geek
 * @since 2019-08-15
 */
public interface BookMapper extends BaseMapper<Book> {

  int updateStockById(@Param("id") Long id, @Param("count") Integer count);

}
