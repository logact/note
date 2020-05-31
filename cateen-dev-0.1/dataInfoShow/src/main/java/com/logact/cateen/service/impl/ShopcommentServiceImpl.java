package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.ShopcommentDao;
import com.logact.cateen.entity.ShopcommentEntity;
import com.logact.cateen.service.ShopcommentService;


@Service("shopcommentService")
public class ShopcommentServiceImpl extends ServiceImpl<ShopcommentDao, ShopcommentEntity> implements ShopcommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ShopcommentEntity> page = this.page(
                new Query<ShopcommentEntity>().getPage(params),
                new QueryWrapper<ShopcommentEntity>()
        );

        return new PageUtils(page);
    }

}