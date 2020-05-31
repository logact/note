package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.ShopDao;
import com.logact.cateen.entity.ShopEntity;
import com.logact.cateen.service.ShopService;


@Service("shopService")
public class ShopServiceImpl extends ServiceImpl<ShopDao, ShopEntity> implements ShopService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShopEntity> page = this.page(
                new Query<ShopEntity>().getPage(params),
                new QueryWrapper<ShopEntity>()
        );

        return new PageUtils(page);
    }

}