package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all=cmsPageRepository.findAll();
        System.out.println(all);
    }

    //分页查询
    @Test
    public void testFindPage(){
        int page=0;
        int size=10;
        Pageable pageRequest = PageRequest.of(page, size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageRequest);
        System.out.println(all);
    }
    @Test
    public void testUpdate(){
        Optional<CmsPage> optional = cmsPageRepository.findById("5abefd525b05aa293098fca6");
        CmsPage cmsPage = optional.get();
        cmsPage.setPageAliase("test01");
        CmsPage save = cmsPageRepository.save(cmsPage);
        System.out.println(save);
    }
    @Test
    public void testFindByPageName(){
        CmsPage cmsPage = cmsPageRepository.findByPageName("index.html");
        System.out.println(cmsPage);
    }
}