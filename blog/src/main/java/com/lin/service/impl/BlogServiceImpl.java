package com.lin.service.impl;

import com.lin.entity.Blog;
import com.lin.mapper.BlogMapper;
import com.lin.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linwar
 * @since 2020-07-25
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
