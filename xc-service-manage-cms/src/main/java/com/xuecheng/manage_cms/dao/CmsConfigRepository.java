package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zhouzhu
 * @Description
 * @create 2019-08-14 17:22
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {

}
