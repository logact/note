package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.CollectactivityDao;
import com.logact.cateen.entity.CollectactivityEntity;
import com.logact.cateen.service.CollectactivityService;


@Service("collectactivityService")
public class CollectactivityServiceImpl extends ServiceImpl<CollectactivityDao, CollectactivityEntity> implements CollectactivityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectactivityEntity> page = this.page(
                new Query<CollectactivityEntity>().getPage(params),
                new QueryWrapper<CollectactivityEntity>()
        );

        return new PageUtils(page);
    }

}