package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.BlogcommentDao;
import com.logact.cateen.entity.BlogcommentEntity;
import com.logact.cateen.service.BlogcommentService;


@Service("blogcommentService")
public class BlogcommentServiceImpl extends ServiceImpl<BlogcommentDao, BlogcommentEntity> implements BlogcommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BlogcommentEntity> page = this.page(
                new Query<BlogcommentEntity>().getPage(params),
                new QueryWrapper<BlogcommentEntity>()
        );

        return new PageUtils(page);
    }

}