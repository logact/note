package com.logact.cateen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logact.common.utils.PageUtils;
import com.logact.cateen.entity.LablefoodEntity;

import java.util.Map;

/**
 * 
 *
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 11:11:40
 */
public interface LablefoodService extends IService<LablefoodEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

