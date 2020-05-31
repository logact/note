package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.BlogDao;
import com.logact.cateen.entity.BlogEntity;
import com.logact.cateen.service.BlogService;


@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogDao, BlogEntity> implements BlogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BlogEntity> page = this.page(
                new Query<BlogEntity>().getPage(params),
                new QueryWrapper<BlogEntity>()
        );

        return new PageUtils(page);
    }

}