package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author zhouzhu
 * @Description
 * @create 2019-07-27 15:40
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;

    /**
     * 页面查询方法
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
     public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
         if (page<=0){
             page=1;
         }
         if (size<=0){
             size=10;
         }
         page=page-1;
         Pageable pageable = PageRequest.of(page, size);
         Page<CmsPage> all = cmsPageRepository.findAll(pageable);
         QueryResult queryResult=new QueryResult();
         //数据列表
         queryResult.setList(all.getContent());
         //数据总记录数
         queryResult.setTotal(all.getTotalElements());
         return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
     }
}
