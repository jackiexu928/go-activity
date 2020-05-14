package com.jackie.goactivity.dao.mongodb;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;


/**
 *
 * @param <T>
 */
public interface MongoBaseDao<T> {
	/**
	 * 插入
	 */
	 T save(T entity);

	/**
	 * 根据ID查询
	 */
	 T findById(String id);
	/**
	 * 获得所有该类型记录
	 */
	 List<T> findAll();
	/**
	 * 根据条件查询
	 */
	 List<T> find(Query query);

	/**
	 * 根据条件查询一个
	 */
	 T findOne(Query query);

	/**
	 * 分页查询
	 */
	//public Page<T> findPage(Page<T> page, Query query);
	/**
	 * 根据条件 获得总数
	 */
	 long count(Query query);


	/**
	 * 更新符合条件并sort之后的第一个文档 并返回更新后的文档
	 */
	 T updateOne(Query query, Update update);
	
	 void update(Query query, Update update);
	/**
	 * 根据条件 删除
	 * 
	 * @param query
	 */
	 void remove(Query query);
	
	 void batch(List<T> entitys);

	MongoTemplate getMongoTemplate();

}
