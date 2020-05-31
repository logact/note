package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.CollectshopDao;
import com.logact.cateen.entity.CollectshopEntity;
import com.logact.cateen.service.CollectshopService;


@Service("collectshopService")
public class CollectshopServiceImpl extends ServiceImpl<CollectshopDao, CollectshopEntity> implements CollectshopService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CollectshopEntity> page = this.page(
                new Query<CollectshopEntity>().getPage(params),
                new QueryWrapper<CollectshopEntity>()
        );

        return new PageUtils(page);
    }

}