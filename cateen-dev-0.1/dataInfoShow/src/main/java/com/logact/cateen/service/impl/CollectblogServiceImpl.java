package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.CollectblogDao;
import com.logact.cateen.entity.CollectblogEntity;
import com.logact.cateen.service.CollectblogService;


@Service("collectblogService")
public class CollectblogServiceImpl extends ServiceImpl<CollectblogDao, CollectblogEntity> implements CollectblogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectblogEntity> page = this.page(
                new Query<CollectblogEntity>().getPage(params),
                new QueryWrapper<CollectblogEntity>()
        );

        return new PageUtils(page);
    }

}