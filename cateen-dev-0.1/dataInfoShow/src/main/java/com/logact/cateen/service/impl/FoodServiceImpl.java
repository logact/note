package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.FoodDao;
import com.logact.cateen.entity.FoodEntity;
import com.logact.cateen.service.FoodService;


@Service("foodService")
public class FoodServiceImpl extends ServiceImpl<FoodDao, FoodEntity> implements FoodService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FoodEntity> page = this.page(
                new Query<FoodEntity>().getPage(params),
                new QueryWrapper<FoodEntity>()
        );

        return new PageUtils(page);
    }

}