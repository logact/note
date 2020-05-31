package com.logact.cateen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logact.common.utils.PageUtils;
import com.logact.cateen.entity.CollectblogEntity;

import java.util.Map;

/**
 * 
 *
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 18:15:48
 */
public interface CollectblogService extends IService<CollectblogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

