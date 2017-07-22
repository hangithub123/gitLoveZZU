package com.zzu.dao.impl;

import org.springframework.stereotype.Repository;

import com.zzu.dao.AdvisoryDao;

import life.treeHole.common.dao.impl.BaseDaoHibernate5;
@Repository(value="advisoryDaoHibernate5")
public class AdvisoryDaoHibernate5 <T> extends BaseDaoHibernate5<T> implements AdvisoryDao<T> {

} 
 