package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.ShoperDao;
import com.logact.cateen.entity.ShoperEntity;
import com.logact.cateen.service.ShoperService;


@Service("shoperService")
public class ShoperServiceImpl extends ServiceImpl<ShoperDao, ShoperEntity> implements ShoperService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShoperEntity> page = this.page(
                new Query<ShoperEntity>().getPage(params),
                new QueryWrapper<ShoperEntity>()
        );

        return new PageUtils(page);
    }

}