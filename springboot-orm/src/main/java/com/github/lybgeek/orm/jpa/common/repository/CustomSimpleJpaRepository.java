package com.github.lybgeek.orm.jpa.common.repository;


import com.github.lybgeek.orm.common.util.BeanUtil;
import com.github.lybgeek.orm.jpa.common.annotation.IgnoreNullValue;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/*
   直接调用原生Save方法会导致null属性覆盖到数据库,因此进行重写
 */
public class CustomSimpleJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> {
 
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
 
    @Autowired
    public CustomSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    /**
     * 通用save方法 ：新增/选择性更新
     */
    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        //获取ID
        ID entityId = (ID) entityInformation.getId(entity);
        Optional<T> optionalT;
        if (entityId == null) {

            //标记为新增数据
            optionalT = Optional.empty();
        } else {
            //若ID非空 则查询最新数据
            optionalT = findById(entityId);
        }
        //若根据ID查询结果为空
        if (!optionalT.isPresent()) {
            em.persist(entity);//新增
            return entity;
        } else {
            if(entity.getClass().isAnnotationPresent(IgnoreNullValue.class)){
                IgnoreNullValue nullValue = entity.getClass().getAnnotation(IgnoreNullValue.class);
                if(nullValue.value()){
                    //1.获取最新对象
                    T target = optionalT.get();
                    //2.将非空属性覆盖到最新对象
                    BeanUtil.copyNotNUllProperties(entity,target);
                    //3.更新非空属性
                    em.merge(target);
                    return (S) target;
                }else{
                    em.merge(entity);
                }
            }else{
                em.merge(entity);
            }

            return entity;
        }
    }


}

