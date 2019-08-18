package com.github.lybgeek.orm.mybatis.service.impl;

import com.github.dozermapper.core.Mapper;
import com.github.lybgeek.orm.common.exception.BizException;
import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.common.util.BeanMapperUtils;
import com.github.lybgeek.orm.common.util.PageUtil;
import com.github.lybgeek.orm.mybatis.dao.BookOrderDao;
import com.github.lybgeek.orm.mybatis.dto.BookOrderDTO;
import com.github.lybgeek.orm.mybatis.dto.OrderItemsDTO;
import com.github.lybgeek.orm.mybatis.model.BookOrder;
import com.github.lybgeek.orm.mybatis.service.BookOrderService;
import com.github.lybgeek.orm.mybatis.util.OrderItemUtil;
import com.github.lybgeek.orm.mybatisplus.dto.BookDTO;
import com.github.lybgeek.orm.mybatisplus.service.BookService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class BookOrderServiceImpl implements BookOrderService, ApplicationEventPublisherAware {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private BookOrderDao bookOrderDao;

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private BookService bookService;

    @Override
    @Transactional
    public BookOrderDTO createBookOrder(BookOrderDTO bookOrderDTO) {
        BookOrder bookOrder = dozerMapper.map(bookOrderDTO,BookOrder.class);
        OrderItemsDTO orderItemsDTO = OrderItemUtil.INSTANCE.getOrderItems(bookOrder.getBookOrderItems());
        Map<String,Integer> orderCountMap = orderItemsDTO.getItemContMap();
        boolean isCanCreate = true;
        String bookName = null;
        for(Map.Entry<String,Integer> entry : orderCountMap.entrySet()){
            BookDTO bookDTO = bookService.getBookByName(entry.getKey());
            if(ObjectUtils.isEmpty(bookDTO)){
                log.warn("不存在书目{}",entry.getKey());
                bookName = entry.getKey();
                isCanCreate = false;
                break;
            }

           int updateBookCount = bookService.updateStockById(bookDTO.getId(),entry.getValue());
            if(updateBookCount <= 0){
                log.warn("当前书目:{},还有库存：{}，欲购数目:{}",entry.getKey(),bookDTO.getStock(),entry.getValue());
                throw new BizException("当前书目->"+entry.getKey()+"已经售罄");
            }
        }

        if(!isCanCreate){
            throw new BizException("不存在书目->"+bookName);
        }

        bookOrder.setTotal(orderItemsDTO.getTotalPrice());
        bookOrder.setOrderNo(String.valueOf(System.currentTimeMillis()));

        BookOrder dbBookOrder = bookOrderDao.createBookOrder(bookOrder);
        applicationEventPublisher.publishEvent(dbBookOrder);

        return dozerMapper.map(dbBookOrder,BookOrderDTO.class);
    }

    @Override
    @Transactional
    public BookOrderDTO editBookOrder(BookOrderDTO bookOrderDTO) {
        BookOrder bookOrder = dozerMapper.map(bookOrderDTO,BookOrder.class);
        BookOrder dbBookOrder = bookOrderDao.editBookOrder(bookOrder);
        return dozerMapper.map(dbBookOrder,BookOrderDTO.class);
    }

    @Override
    @Transactional
    public boolean delBookOrderById(Long id) {
        return bookOrderDao.delBookOrderById(id);
    }

    @Override
    public PageResult<BookOrderDTO> pageBookOrder(PageQuery<BookOrderDTO> pageQuery) {
        Integer pageNo = ObjectUtils.isEmpty(pageQuery.getPageNo()) ? 1 : pageQuery.getPageNo();
        Integer pageSize = ObjectUtils.isEmpty(pageQuery.getPageSize()) ? 5 : pageQuery.getPageSize();
        BookOrderDTO bookOrderDTO = pageQuery.getQueryParams();
        Page<BookOrder> page = PageHelper.startPage(pageNo,pageSize);
        PageHelper.orderBy("bo.create_date DESC");
        List<BookOrderDTO> list = listBookOrders(bookOrderDTO);

        return PageUtil.INSTANCE.getPage(page,list);
    }

    @Override
    public List<BookOrderDTO> listBookOrders(BookOrderDTO bookOrderDTO) {
        BookOrder bookOrder = new BookOrder();
        if(bookOrderDTO != null){
            bookOrder = dozerMapper.map(bookOrderDTO,BookOrder.class);
        }
        List<BookOrder> bookOrders = bookOrderDao.listBookOrders(bookOrder);
        if(CollectionUtils.isNotEmpty(bookOrders)){
            return BeanMapperUtils.mapList(bookOrders,BookOrderDTO.class);
        }
        return Collections.emptyList();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
