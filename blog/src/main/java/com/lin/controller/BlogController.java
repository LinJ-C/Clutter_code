package com.lin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.common.lang.Result;
import com.lin.entity.Blog;
import com.lin.service.BlogService;
import com.lin.service.UserService;
import com.lin.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linwar
 * @since 2020-07-24
 */
@RestController
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    //查询状态为1的所有文章，1为公开文章
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page,new QueryWrapper<Blog>().eq("status",'1').orderByDesc("created"));

        return Result.succ(pageData);
    }

    //查询相应类似的文章
    @GetMapping("/blogs/{type}")
    public Result typelist(@PathVariable(name = "type") String type,@RequestParam(defaultValue = "1") Integer currentPage ){

        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page,new QueryWrapper<Blog>().eq("status",'1').eq("type",type).orderByDesc("created"));
        return Result.succ(pageData);
    }

    //作者自己查看自己隐藏的文章
    @RequiresAuthentication
    @GetMapping("/ownblog")
    public Result ownlist(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page,new QueryWrapper<Blog>().eq("user_id",ShiroUtil.getProfile().getId()).eq("status",'0'));
        Assert.isTrue(ShiroUtil.getProfile().getId()!=null,"没有此用户");
        return Result.succ(pageData);
    }

    //查看某一条可见的文章详细
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该内容已被删除");

        return  Result.succ(blog);
    }

    //删除某一篇文章，只有该文章作者才有此权力
    @RequiresAuthentication
    @GetMapping("/blog/del/{id}")
    public Result del(@PathVariable(name = "id") Long id){
        Blog temp = blogService.getById(id);
        //只能删除自己的文章
        Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限删除此文章");
        Assert.isTrue(blogService.removeById(id),"删除失败！");

        return  Result.succ("成功删除！");
    }

    //置顶某一篇文章，只有该文章作者才有此权力
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

    //修改以及编辑文章，登录才有用，文章作者只能更改自己的文章
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp=null;
        if (blog.getId()!=null){
            temp = blogService.getById(blog.getId());
            blog.setLastUpdata(LocalDateTime.now());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(),"没有权限编辑");
        }else{
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setUsername(ShiroUtil.getProfile().getUsername());
            temp.setCreated(LocalDateTime.now());
            //temp.setStatus(1); 此字段自己设置，由前端传过来
        }

        BeanUtil.copyProperties(blog,temp,"id","userId","created","username");
        blogService.saveOrUpdate(temp);

        return Result.succ(null);

    }

    //查询所有类似
    @GetMapping("/types")
    public Result types(){
        List<Blog> typelist =  blogService.list(new QueryWrapper<Blog>().select("type"));
        return Result.succ(typelist);
    }


}
