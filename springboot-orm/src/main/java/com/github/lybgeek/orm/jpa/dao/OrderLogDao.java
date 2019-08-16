package com.github.lybgeek.orm.jpa.dao;

import com.github.lybgeek.orm.common.model.PageQuery;
import com.github.lybgeek.orm.common.model.PageResult;
import com.github.lybgeek.orm.common.util.PageUtil;
import com.github.lybgeek.orm.jpa.model.OrderLog;
import com.github.lybgeek.orm.jpa.repository.OrderLogRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class OrderLogDao {

  @Autowired
  private OrderLogRepository orderLogRepository;

  public void saveLog(OrderLog orderLog) {
     orderLogRepository.save(orderLog);
  }

  public PageResult<OrderLog> pageOrderLogs(PageQuery<OrderLog> pageQuery) {

    Sort sort = new Sort(Direction.DESC,"createDate");
    Pageable pageable = PageRequest.of(pageQuery.getPageNo() - 1,pageQuery.getPageSize(),sort);

    Specification<OrderLog> specification = (Specification<OrderLog>) (root, criteriaQuery, criteriaBuilder) -> {

      OrderLog queryParams = pageQuery.getQueryParams();
      if(queryParams != null){
        List<Predicate> predicates = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(queryParams.getOrderId())){
          Path<Long> orderId = root.get("orderId");
          Predicate orderIdPredicate = criteriaBuilder.equal(orderId,queryParams.getOrderId());
          predicates.add(orderIdPredicate);
        }

        if(StringUtils.isNotBlank(queryParams.getOrderName())){
          Path<String> orderName = root.get("orderName");
          Predicate orderNamePredicate = criteriaBuilder.like(orderName,"%"+queryParams.getOrderName()+"%");
          predicates.add(orderNamePredicate);
        }

        if(CollectionUtils.isNotEmpty(predicates)){
          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }
      }


      return null;
    };

    Page<OrderLog> orderLogPage = orderLogRepository.findAll(specification,pageable);
    if(ObjectUtils.isNotEmpty(orderLogPage)){
      return PageUtil.INSTANCE.getPage(orderLogPage);
    }

    return null;
  }

}
