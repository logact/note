package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.FoodcommentDao;
import com.logact.cateen.entity.FoodcommentEntity;
import com.logact.cateen.service.FoodcommentService;


@Service("foodcommentService")
public class FoodcommentServiceImpl extends ServiceImpl<FoodcommentDao, FoodcommentEntity> implements FoodcommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FoodcommentEntity> page = this.page(
                new Query<FoodcommentEntity>().getPage(params),
                new QueryWrapper<FoodcommentEntity>()
        );

        return new PageUtils(page);
    }

}