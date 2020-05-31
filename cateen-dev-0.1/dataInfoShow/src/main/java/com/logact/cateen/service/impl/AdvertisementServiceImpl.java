package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.AdvertisementDao;
import com.logact.cateen.entity.AdvertisementEntity;
import com.logact.cateen.service.AdvertisementService;


@Service("advertisementService")
public class AdvertisementServiceImpl extends ServiceImpl<AdvertisementDao, AdvertisementEntity> implements AdvertisementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdvertisementEntity> page = this.page(
                new Query<AdvertisementEntity>().getPage(params),
                new QueryWrapper<AdvertisementEntity>()
        );

        return new PageUtils(page);
    }

}