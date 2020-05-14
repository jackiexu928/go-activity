package com.jackie.goactivity.dao.impl;

import com.jackie.goactivity.dao.TemplateDao;
import com.jackie.goactivity.dao.mongodb.MongoBaseDaoImpl;
import com.jackie.goactivity.entity.Template;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-11
 */
@Repository
public class TemplateDaoImpl extends MongoBaseDaoImpl<Template> implements TemplateDao {
}
