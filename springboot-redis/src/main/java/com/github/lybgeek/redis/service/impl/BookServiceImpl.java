package com.github.lybgeek.redis.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.github.lybgeek.common.exception.BizException;
import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.common.util.BeanMapperUtils;
import com.github.lybgeek.common.util.PageUtil;

import com.github.lybgeek.redis.annotation.RedisCache;
import com.github.lybgeek.redis.dao.BookMapper;
import com.github.lybgeek.redis.dto.BookDTO;
import com.github.lybgeek.redis.enu.CacheOperateType;
import com.github.lybgeek.redis.model.Book;
import com.github.lybgeek.redis.service.BookService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyb-geek
 * @since 2019-08-15
 */
@Service
@Slf4j
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

  @Autowired
  private BookMapper bookMapper;

  @Autowired
  private Mapper dozerMapper;

  @Autowired
  private RedisLockRegistry redisLockRegistry;

  @Override
  @Transactional
  @Cacheable(cacheNames = "book",key="'add_'+#bookDTO.bookName")
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
 // @CachePut(cacheNames = "book",key="'edit_'+#id")
  @RedisCache(type=CacheOperateType.UPDTAE,cacheKeyPrefix = "editBook",expireTime = 120)
  public BookDTO editBook(BookDTO bookDTO) {
    Book book = dozerMapper.map(bookDTO,Book.class);
    book.setUpdateDate(new Date());
    baseMapper.updateById(book);


    return getBookById(book.getId());
  }

  @Override
  @Transactional
  @CacheEvict(cacheNames = "book",key="'del_'+#id",allEntries = true)
  //@RedisCache(type= CacheOperateType.DELETE,cacheKeyPrefix = "pageBook",allEntries = true)
  public boolean delBookById(Long id) {

    int count = baseMapper.deleteById(id);

    return count > 0;
  }

  @Override
  @RedisCache(type= CacheOperateType.QUERY,cacheKeyPrefix = "pageBook",expireTime = 180)
  public PageResult<BookDTO> pageBook(PageQuery<BookDTO> pageQuery) {
    log.info("pageBook走db");
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
  @RedisCache(type = CacheOperateType.QUERY,cacheKeyPrefix = "listBooks",expireTime = 180)
  public List<BookDTO> listBooks(BookDTO bookDTO) {
    log.info("listBooks走db");
    Wrapper<Book> wrapper = wrapperQueryCondition(bookDTO);

    List<Book> books = baseMapper.selectList(wrapper);

    if(CollectionUtils.isNotEmpty(books)){
      return BeanMapperUtils.mapList(books,BookDTO.class);
    }

    return null;
  }

  @Override
  @Cacheable(cacheNames="book",key="#id",unless="#result == null")
  public BookDTO getBookById(Long id) {
    log.info("getBookById走db");
    Book dbBook = baseMapper.selectById(id);
    BookDTO bookDTO = dozerMapper.map(dbBook,BookDTO.class);
    return bookDTO;
  }

  @Override
  @Transactional
  @CachePut(cacheNames = "book",key="'stock_'+#id")
  public int updateStockById(Long id, Integer count) {
    int num = 0;
    Lock lock = redisLockRegistry.obtain("bookStock");
    if(lock.tryLock()) {
      try {
        log.info("updateStockById走db");
        num = bookMapper.updateStockById(id, count);
      } catch (Exception e) {
        log.error("updateStockById error:" + e.getMessage(), e);
      } finally {
        lock.unlock();
      }
    }
    return num;
  }

  @Override
  @RedisCache(type = CacheOperateType.QUERY,expireTime = 180)
  public BookDTO getBookByName(String bookName) {
    log.info("getBookByName走db");
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
