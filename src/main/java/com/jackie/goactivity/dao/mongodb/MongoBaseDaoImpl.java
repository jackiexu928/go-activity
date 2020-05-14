package com.jackie.goactivity.dao.mongodb;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public abstract class MongoBaseDaoImpl<T> implements MongoBaseDao<T>{
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public T save(T entity) {
		mongoTemplate.insert(entity);
        return entity;
	}

	@Override
	public T findById(String id) {
		 return mongoTemplate.findById(id, this.getEntityClass());
	}

	@Override
	public List<T> findAll() {
		return mongoTemplate.findAll(this.getEntityClass());
	}

	@Override
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	@Override
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}
	
	@Override
	public void batch(List<T> entitys) {
		mongoTemplate.insertAll(entitys);
	}

	/*@Override
	public Page<T> findPage(Page<T> page, Query query) {
		//如果没有条件 则所有全部
        query=query==null?new Query(Criteria.where("_id").exists(true)):query;
        long count = this.count(query);
        // 总数
        page.setTotal((int) count);
        int currentPage = page.getCurrentPage();
        int pageSize = page.getPerPageSize();
        query.skip((currentPage - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        page.setRows(rows);
        return page;
	}*/

	@Override
	public long count(Query query) {
		 return mongoTemplate.count(query, this.getEntityClass());
	}

	@Override
	public T updateOne(Query query, Update update) {
		Assert.notNull(update, "修改的信息不能为空");
	    return mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	public void update(Query query, Update update) {
		mongoTemplate.updateMulti(query, update, this.getEntityClass());
	}
	
	@Override
	public void remove(Query query) {
		mongoTemplate.remove(query, this.getEntityClass());
	}

	private Class<T> getEntityClass() {
		Class clazz = getClass();
		//返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return (Class<T>) Object.class;
		}
		//返回表示此类型实际类型参数的 Type 对象的数组。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return (Class<T>) (Class<Object>) params[0];
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
