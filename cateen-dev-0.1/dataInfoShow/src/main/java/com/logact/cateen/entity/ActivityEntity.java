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
@TableName("activity")
public class ActivityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 活动的标题
	 */
	private String title;
	/**
	 * 活动的宣传图
	 */
	private String imgsrc;
	/**
	 * 活动的状态
	 */
	private String status;
	/**
	 * 活动开始的时间
	 */
	private Date starttime;
	/**
	 * 活动的结束时间
	 */
	private Date endtime;
	/**
	 * 活动的发起者名字
	 */
	private String initiator;
	/**
	 * 活动的发起者id
	 */
	private Integer initiatorid;
	/**
	 * 简述
	 */
	private String simpledesc;
	/**
	 * 详细描述
	 */
	private String descr;
	/**
	 * 分数的限制
	 */
	private Integer scorelimit;
	/**
	 * 限制描述
	 */
	private String limitdesc;
	/**
	 * 等级限制
	 */
	private Integer levellimit;
	/**
	 * 活动状态码
	 */
	private Integer statuscode;
	/**
	 * 活动的类型
	 */
	private Integer type;
	private Integer checked;
}
