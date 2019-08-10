package com.github.lybgeek.mongodb.common.repository;


import com.github.lybgeek.mongodb.common.annotation.IgnoreNullValue;
import com.github.lybgeek.mongodb.common.util.BeanUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

public class CustomSimpleMongodbRepository<T,ID> extends SimpleMongoRepository<T, ID> {


    private final MongoEntityInformation<T,?> entityInformation;

    private final MongoOperations mongoOperations;

    @Autowired
    public CustomSimpleMongodbRepository(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.entityInformation = metadata;
        this.mongoOperations = mongoOperations;
    }





    @Override
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
            mongoOperations.insert(entity,entityInformation.getCollectionName());//新增
            return entity;
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(entityId));
            if(entity.getClass().isAnnotationPresent(IgnoreNullValue.class)){
                IgnoreNullValue nullValue = entity.getClass().getAnnotation(IgnoreNullValue.class);
                if(nullValue.value()){
                    //1.获取最新对象
                    T target = optionalT.get();
                    //2.将非空属性覆盖到最新对象
                    BeanUtil.copyNotNUllProperties(entity,target);
                    //3.更新非空属性
                    mongoOperations.findAndReplace(query,target,entityInformation.getCollectionName());
                    return (S)target;
                }else{
                    mongoOperations.findAndReplace(query,entity,entityInformation.getCollectionName());
                }
            }else{
                mongoOperations.findAndReplace(query,entity,entityInformation.getCollectionName());
            }

            return entity;
        }
    }


}
