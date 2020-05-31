package com.logact.cateen.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.Query;

import com.logact.cateen.dao.ManagerDao;
import com.logact.cateen.entity.ManagerEntity;
import com.logact.cateen.service.ManagerService;


@Service("managerService")
public class ManagerServiceImpl extends ServiceImpl<ManagerDao, ManagerEntity> implements ManagerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ManagerEntity> page = this.page(
                new Query<ManagerEntity>().getPage(params),
                new QueryWrapper<ManagerEntity>()
        );

        return new PageUtils(page);
    }

}