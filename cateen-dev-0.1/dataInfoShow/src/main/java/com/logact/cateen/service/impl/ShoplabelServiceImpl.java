package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.ShoplabelDao;
import com.logact.cateen.entity.ShoplabelEntity;
import com.logact.cateen.service.ShoplabelService;


@Service("shoplabelService")
public class ShoplabelServiceImpl extends ServiceImpl<ShoplabelDao, ShoplabelEntity> implements ShoplabelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShoplabelEntity> page = this.page(
                new Query<ShoplabelEntity>().getPage(params),
                new QueryWrapper<ShoplabelEntity>()
        );

        return new PageUtils(page);
    }

}