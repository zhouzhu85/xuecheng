package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
         if (queryPageRequest==null){
             queryPageRequest=new QueryPageRequest();
         }
         //自定义条件查询
         //定义条件匹配器
         ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
         //条件对象
         CmsPage cmsPage = new CmsPage();
         /** 设置条件值 **/
         //设置站点id
         if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
             cmsPage.setSiteId(queryPageRequest.getSiteId());
         }
         //设置模板id
         if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
             cmsPage.setTemplateId(queryPageRequest.getTemplateId());
         }
         //设置页面别名
         if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
             cmsPage.setPageAliase(queryPageRequest.getPageAliase());
         }
         //定义条件对象
         Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
         //分页参数
         if (page<=0){
             page=1;
         }
         if (size<=0){
             size=10;
         }
         page=page-1;
         Pageable pageable = PageRequest.of(page, size);
         //实现自定义条件查询并分页
         Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
         QueryResult queryResult=new QueryResult();
         //数据列表
         queryResult.setList(all.getContent());
         //数据总记录数
         queryResult.setTotal(all.getTotalElements());
         return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
     }
}
