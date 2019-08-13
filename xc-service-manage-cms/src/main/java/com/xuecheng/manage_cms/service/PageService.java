package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     public CmsPageResult add(CmsPage cmsPage){
         //校验页面名称、站点id、页面webpath的唯一性
         //根据页面名称、站点id、页面webpath去查询cms_page集合，如果查到说明此页面已经存在，如果查询不到再继续添加
         CmsPage oldCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
         if (oldCmsPage!=null){
             ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
         }
         cmsPage.setPageId(null);
         CmsPage pageList = cmsPageRepository.save(cmsPage);
         return new CmsPageResult(CommonCode.SUCCESS,pageList);
     }

    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public CmsPageResult update(String id, CmsPage cmsPage) {
         CmsPage one=this.findById(id);
         if (one!=null){
             one.setTemplateId(cmsPage.getTemplateId());
             one.setSiteId(cmsPage.getSiteId());
             one.setPageAliase(cmsPage.getPageAliase());
             one.setPageName(cmsPage.getPageName());
             one.setPageWebPath(cmsPage.getPageWebPath());
             one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
             one.setPageType(cmsPage.getPageType());
             CmsPage save = cmsPageRepository.save(one);
             if (save!=null){
                 CmsPageResult cmsPageResult=new CmsPageResult(CommonCode.SUCCESS,save);
                 return cmsPageResult;
             }
         }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    public ResponseResult delete(String id) {
        CmsPage one = this.findById(id);
        if (one!=null){
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
