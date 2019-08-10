package com.github.lybgeek.mongodb.common.id.listener;

import com.github.lybgeek.mongodb.common.id.annotation.GeneratedValue;
import com.github.lybgeek.mongodb.common.id.model.SequenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class GeneratedValueMongodbEventListener extends AbstractMongoEventListener<Object> {

  @Autowired
  private MongoTemplate mongoTemplate;

  /**
   * 调用MongoTemplate的insert、insertList和save操作，在通过MongoConverter将对象转换为文档之前的处理。
   * @param event
   */
  @Override
  public void onBeforeConvert(BeforeConvertEvent<Object> event) {

    Object source = event.getSource();
    if(source != null) {
      ReflectionUtils.doWithFields(source.getClass(), field -> {
        ReflectionUtils.makeAccessible(field);
        if (field.isAnnotationPresent(GeneratedValue.class)) {
          //设置自增ID
          boolean hasDocumentAnnotation = source.getClass().isAnnotationPresent(Document.class);
          String collectionName = source.getClass().getSimpleName();
          if(hasDocumentAnnotation){
            Document document = source.getClass().getAnnotation(Document.class);
            collectionName = document.value();
          }

          field.set(source, getNextId(collectionName));
        }
      });
    }
  }

  /**
   * 调用MongoTemplate的insert、insertList和save操作，在数据库中插入或保存文档之前的处理。
   * @param event
   */
  @Override
  public void onBeforeSave(BeforeSaveEvent<Object> event) {

    super.onBeforeSave(event);
  }

  /**
   * 调用MongoTemplate的insert、insertList和save操作，在数据库中插入或保存文档之后的处理。
   * @param event
   */
  @Override
  public void onAfterSave(AfterSaveEvent<Object> event) {

    super.onAfterSave(event);
  }

  /**
   * 调用MongoTemplate中的find、findAndRemove、findOne和getCollection方法，从数据库检索文档后的处理。
   * @param event
   */
  @Override
  public void onAfterLoad(AfterLoadEvent<Object> event) {

    super.onAfterLoad(event);
  }

  /**
   * 调用MongoTemplate中的find、findAndRemove、findOne和getCollection方法，从数据库检索文档被转换为POJO后的处理。
   * @param event
   */
  @Override
  public void onAfterConvert(AfterConvertEvent<Object> event) {

    super.onAfterConvert(event);
  }

  /**
   * 获取下一个自增ID
   * @param collName  集合名
   * @return
   */
  private Long getNextId(String collName) {
    Query query = new Query(Criteria.where("collName").is(collName));
    Update update = new Update();
    update.inc("seqId", 1);
    FindAndModifyOptions options = new FindAndModifyOptions();
    options.upsert(true);
    options.returnNew(true);
    SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
    return seqId.getSeqId();
  }
}
