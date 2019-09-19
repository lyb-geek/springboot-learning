package com.github.lybgeek.shorturl.dao;

import com.github.lybgeek.common.model.PageQuery;
import com.github.lybgeek.common.model.PageResult;
import com.github.lybgeek.common.util.PageUtil;
import com.github.lybgeek.shorturl.model.ShortUrl;
import com.github.lybgeek.shorturl.repository.ShortUrlRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class ShortUrlDao {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public ShortUrl save(ShortUrl shortUrl){
        return shortUrlRepository.save(shortUrl);
    }

    public ShortUrl getShortUrlById(Long id){
        return shortUrlRepository.getOne(id);
    }


    public PageResult<ShortUrl> pageShortUrls(PageQuery<ShortUrl> pageQuery) {

        Sort sort = new Sort(Direction.DESC,"id");
        Pageable pageable = PageRequest.of(pageQuery.getPageNo() - 1,pageQuery.getPageSize(),sort);

        Specification<ShortUrl> specification = (Specification<ShortUrl>) (root, criteriaQuery, criteriaBuilder) -> {

            ShortUrl queryParams = pageQuery.getQueryParams();
            if(queryParams != null){
                List<Predicate> predicates = getPredicates(root, criteriaBuilder, queryParams);

                if(CollectionUtils.isNotEmpty(predicates)){
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }
            }


            return null;
        };

        Page<ShortUrl> shortUrlPage = shortUrlRepository.findAll(specification,pageable);
        if(ObjectUtils.isNotEmpty(shortUrlPage)){
            return PageUtil.INSTANCE.getPage(shortUrlPage);
        }

        return null;
    }

    private List<Predicate> getPredicates(Root<ShortUrl> root, CriteriaBuilder criteriaBuilder,
        ShortUrl queryParams) {

        List<Predicate> predicates = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(queryParams.getUrlName())){
            Path<String> urlName = root.get("urlName");
            Predicate urlNamePredicate = criteriaBuilder.like(urlName,"%"+queryParams.getUrlName()+"%");
            predicates.add(urlNamePredicate);
        }

        if(StringUtils.isNotBlank(queryParams.getRemark())){
            Path<String> remark = root.get("remark");
            Predicate remarkPredicate = criteriaBuilder.like(remark,"%"+queryParams.getRemark()+"%");
            predicates.add(remarkPredicate);
        }
        return predicates;
    }

    public boolean deleteShortUrlById(Long id){
        shortUrlRepository.deleteById(id);
        return !shortUrlRepository.existsById(id);
    }


    public List<ShortUrl> listShortUrls(ShortUrl shortUrl){

        Specification<ShortUrl> specification = (Specification<ShortUrl>) (root, criteriaQuery, criteriaBuilder) -> {

                List<Predicate> predicates = getPredicates(root, criteriaBuilder, shortUrl);
                if(CollectionUtils.isNotEmpty(predicates)){
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }

            return null;
        };

       return shortUrlRepository.findAll(specification);



    }



}
