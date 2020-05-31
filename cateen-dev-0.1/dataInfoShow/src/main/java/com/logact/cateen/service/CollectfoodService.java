package com.logact.cateen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logact.common.utils.PageUtils;
import com.logact.cateen.entity.CollectfoodEntity;

import java.util.Map;

/**
 * 
 *
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 18:15:48
 */
public interface CollectfoodService extends IService<CollectfoodEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

