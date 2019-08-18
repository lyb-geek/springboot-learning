package com.github.lybgeek.orm.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.github.lybgeek.orm.common.exception.BizException;
import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.common.util.BeanMapperUtils;
import com.github.lybgeek.orm.common.util.PageUtil;
import com.github.lybgeek.orm.mybatisplus.dao.BookMapper;
import com.github.lybgeek.orm.mybatisplus.dto.BookDTO;
import com.github.lybgeek.orm.mybatisplus.model.Book;
import com.github.lybgeek.orm.mybatisplus.service.BookService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyb-geek
 * @since 2019-08-15
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

  @Autowired
  private BookMapper bookMapper;

  @Autowired
  private Mapper dozerMapper;

  @Override
  @Transactional
  public BookDTO addBook(BookDTO bookDTO) {
    Book book = dozerMapper.map(bookDTO,Book.class);
    boolean isExitBookByName = ObjectUtils.isNotEmpty(getBookByName(bookDTO.getBookName()));
    if(isExitBookByName){
      throw new BizException("书名已经存在");
    }
    book.setCreateDate(new Date());
    book.setUpdateDate(new Date());
    baseMapper.insert(book);

    bookDTO = dozerMapper.map(book,BookDTO.class);

    return bookDTO;
  }

  @Override
  @Transactional
  public BookDTO editBook(BookDTO bookDTO) {
    Book book = dozerMapper.map(bookDTO,Book.class);
    book.setUpdateDate(new Date());
    baseMapper.updateById(book);


    return getBookById(book.getId());
  }

  @Override
  @Transactional
  public boolean delBookById(Long id) {

    int count = baseMapper.deleteById(id);

    return count > 0;
  }

  @Override
  public PageResult<BookDTO> pageBook(PageQuery<BookDTO> pageQuery) {
    BookDTO bookDTO = pageQuery.getQueryParams();
    Wrapper<Book> wrapper = wrapperQueryCondition(bookDTO);
    IPage<Book> page = new Page<>(pageQuery.getPageNo(),pageQuery.getPageSize());
    IPage<Book> bookIPage = baseMapper.selectPage(page,wrapper);
    if(bookIPage != null){
      List<BookDTO> bookDTOS = new ArrayList<>();
      if(CollectionUtils.isNotEmpty(bookIPage.getRecords())){
        bookDTOS = BeanMapperUtils.mapList(bookIPage.getRecords(),BookDTO.class);
      }
      return PageUtil.INSTANCE.getPage(bookIPage,bookDTOS);
    }
    return null;
  }

  @Override
  public List<BookDTO> listBooks(BookDTO bookDTO) {
    Wrapper<Book> wrapper = wrapperQueryCondition(bookDTO);

    List<Book> books = baseMapper.selectList(wrapper);

    if(CollectionUtils.isNotEmpty(books)){
      return BeanMapperUtils.mapList(books,BookDTO.class);
    }

    return null;
  }

  @Override
  public BookDTO getBookById(Long id) {
    Book dbBook = baseMapper.selectById(id);
    BookDTO bookDTO = dozerMapper.map(dbBook,BookDTO.class);
    return bookDTO;
  }

  @Override
  @Transactional
  public int updateStockById(Long id, Integer count) {

    return bookMapper.updateStockById(id,count);
  }

  @Override
  public BookDTO getBookByName(String bookName) {
    Wrapper<Book> wrapper = new QueryWrapper<>();
    ((QueryWrapper<Book>) wrapper).eq("book_name", bookName);
    Book book = bookMapper.selectOne(wrapper);
    if(ObjectUtils.isNotEmpty(book)){
      return dozerMapper.map(book,BookDTO.class);
    }
    return null;
  }

  private Wrapper<Book> wrapperQueryCondition(BookDTO bookDTO){
    Wrapper<Book> wrapper = new QueryWrapper<>();
    if(ObjectUtils.isNotEmpty(bookDTO)) {
      if (ObjectUtils.isNotEmpty(bookDTO.getId())) {
        ((QueryWrapper<Book>) wrapper).eq("id", bookDTO.getId());
      }

      if (StringUtils.isNotBlank(bookDTO.getAuthor())) {
        ((QueryWrapper<Book>) wrapper).like("author", bookDTO.getAuthor());
      }

      if (StringUtils.isNotBlank(bookDTO.getBookName())) {
        ((QueryWrapper<Book>) wrapper).like("book_name", bookDTO.getBookName());
      }
    }
    ((QueryWrapper<Book>) wrapper).orderByDesc("create_date");
    return wrapper;
  }
}
