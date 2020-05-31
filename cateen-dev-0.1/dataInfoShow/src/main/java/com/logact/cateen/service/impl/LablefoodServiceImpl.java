package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.LablefoodDao;
import com.logact.cateen.entity.LablefoodEntity;
import com.logact.cateen.service.LablefoodService;


@Service("lablefoodService")
public class LablefoodServiceImpl extends ServiceImpl<LablefoodDao, LablefoodEntity> implements LablefoodService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LablefoodEntity> page = this.page(
                new Query<LablefoodEntity>().getPage(params),
                new QueryWrapper<LablefoodEntity>()
        );

        return new PageUtils(page);
    }

}