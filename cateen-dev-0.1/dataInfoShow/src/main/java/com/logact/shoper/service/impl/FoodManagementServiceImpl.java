package com.logact.shoper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.logact.cateen.dao.FoodDao;
import com.logact.cateen.entity.FoodEntity;
import com.logact.shoper.service.FoodManagementService;
import org.springframework.stereotype.Service;

/**
 * @author: logact
 * @date: Created in 2020/5/24 16:56
 * @description:
 */
@Service("foodManagementService")
public class FoodManagementServiceImpl extends ServiceImpl<FoodDao, FoodEntity> implements FoodManagementService {
}
