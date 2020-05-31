package com.logact.cateen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.logact.common.utils.PageUtils;
import com.logact.cateen.entity.UserEntity;

import java.util.Map;

/**
 *
 *
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 11:11:40
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);
    UserEntity getUserByName(Map<String, Object> params);
}

