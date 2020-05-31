package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.FoodlabelDao;
import com.logact.cateen.entity.FoodlabelEntity;
import com.logact.cateen.service.FoodlabelService;


@Service("foodlabelService")
public class FoodlabelServiceImpl extends ServiceImpl<FoodlabelDao, FoodlabelEntity> implements FoodlabelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FoodlabelEntity> page = this.page(
                new Query<FoodlabelEntity>().getPage(params),
                new QueryWrapper<FoodlabelEntity>()
        );

        return new PageUtils(page);
    }

}