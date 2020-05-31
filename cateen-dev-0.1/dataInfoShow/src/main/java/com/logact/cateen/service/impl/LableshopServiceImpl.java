package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.LableshopDao;
import com.logact.cateen.entity.LableshopEntity;
import com.logact.cateen.service.LableshopService;


@Service("lableshopService")
public class LableshopServiceImpl extends ServiceImpl<LableshopDao, LableshopEntity> implements LableshopService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LableshopEntity> page = this.page(
                new Query<LableshopEntity>().getPage(params),
                new QueryWrapper<LableshopEntity>()
        );

        return new PageUtils(page);
    }

}