package com.logact.cateen.dao;

import com.logact.cateen.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 11:11:40
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
