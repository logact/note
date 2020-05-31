package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.CollectfoodDao;
import com.logact.cateen.entity.CollectfoodEntity;
import com.logact.cateen.service.CollectfoodService;


@Service("collectfoodService")
public class CollectfoodServiceImpl extends ServiceImpl<CollectfoodDao, CollectfoodEntity> implements CollectfoodService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectfoodEntity> page = this.page(
                new Query<CollectfoodEntity>().getPage(params),
                new QueryWrapper<CollectfoodEntity>()
        );

        return new PageUtils(page);
    }

}