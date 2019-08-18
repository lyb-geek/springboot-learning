package com.github.lybgeek.orm.mybatis.dao;


import com.github.lybgeek.orm.mybatis.model.BookOrder;
import com.github.lybgeek.orm.mybatis.model.BookOrderItem;
import com.github.lybgeek.orm.mybatis.model.BookOrderItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookOrderDao {

    @Autowired
    private BookOrderMapper bookOrderMapper;

    @Autowired
    private BookOrderItemMapper bookOrderItemMapper;


    public BookOrder createBookOrder(BookOrder bookOrder){
         bookOrderMapper.insertSelective(bookOrder);
         List<BookOrderItem> items = bookOrder.getBookOrderItems();
         items.forEach(item->{
             item.setOrderId(bookOrder.getId());
             bookOrderItemMapper.insertSelective(item);
         });

         return bookOrder;


    }

    public BookOrder editBookOrder(BookOrder bookOrder){
       bookOrderMapper.updateByPrimaryKeySelective(bookOrder);
       return bookOrderMapper.selectByPrimaryKey(bookOrder.getId());

    }

    public boolean delBookOrderById(Long id){
        int delOrderCount =  bookOrderMapper.deleteByPrimaryKey(id);
        BookOrderItemExample bookOrderItemExample = new BookOrderItemExample();
        bookOrderItemExample.createCriteria().andOrderIdEqualTo(id);
        int delItemCount = bookOrderItemMapper.deleteByExample(bookOrderItemExample);

        return delOrderCount > 0 && delItemCount > 0;

    }


    public List<BookOrder> listBookOrders(BookOrder bookOrder){
        return bookOrderMapper.selectOrderWithItems(bookOrder);

    }
}
