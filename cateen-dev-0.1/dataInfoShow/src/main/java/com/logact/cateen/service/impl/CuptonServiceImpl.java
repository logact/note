package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.CuptonDao;
import com.logact.cateen.entity.CuptonEntity;
import com.logact.cateen.service.CuptonService;


@Service("cuptonService")
public class CuptonServiceImpl extends ServiceImpl<CuptonDao, CuptonEntity> implements CuptonService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CuptonEntity> page = this.page(
                new Query<CuptonEntity>().getPage(params),
                new QueryWrapper<CuptonEntity>()
        );

        return new PageUtils(page);
    }

}