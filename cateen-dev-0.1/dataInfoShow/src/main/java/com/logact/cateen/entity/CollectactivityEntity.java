package com.logact.cateen.entity;

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
 * @date 2020-05-18 18:15:48
 */
@Data
@TableName("collectactivity")
public class CollectactivityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 收藏的活动id
	 */
	private Integer activityid;
	/**
	 * 收藏的活动名
	 */
	private String activityname;
	/**
	 * 收藏的时间
	 */
	private Date collectdate;
	private Integer userId;

}
