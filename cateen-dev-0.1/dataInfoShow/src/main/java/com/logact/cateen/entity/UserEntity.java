package com.logact.cateen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 *
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 11:11:40
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */

	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 学号
	 */
	private String cno;
	/**
	 * 口味偏好用逗号隔开
	 */
	private String taste;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 校区
	 */
	private String campus;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 用户的姓名
	 */
	private String avatar;
	/**
	 * 用户的级别
	 */
	private Integer levelnum;
	/**
	 * 用户图片
	 */
	private String avatarimg;

}
