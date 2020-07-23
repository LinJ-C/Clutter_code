package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.service.BlogService;
import com.markerhub.util.ShiroUtil;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-07-01
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该内容已被删除");

        return  Result.succ(blog);
    }

    @RequiresAuthentication
    @GetMapping("/blog/del/{id}")
    public Result del(@PathVariable(name = "id") Long id){
        Blog temp = blogService.getById(id);
        //只能删除自己的文章
        Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限删除此文章");
        Assert.isTrue(blogService.removeById(id),"删除失败！");

        return  Result.succ("成功删除！");
    }

    @RequiresAuthentication
    @GetMapping("/blog/top/{id}")
    public Result top(@PathVariable(name = "id") Long id){
        Blog temp = blogService.getById(id);
        //只能置顶自己的文章
        Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限删除此文章");
        temp.setCreated(LocalDateTime.now());
        Assert.isTrue(blogService.saveOrUpdate(temp),"置顶失败！");
        return  Result.succ("置顶成功！");
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp=null;
        if (blog.getId()!=null){
            temp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限编辑");
        }else{
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);

    }



}
