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
 * @date 2020-05-18 11:11:40
 */
@Data
@TableName("cupton")
public class CuptonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 优惠卷的名称
	 */
	private String name;
	/**
	 * 使用限制描述
	 */
	private String limitdesc;
	/**
	 * 使用的积分限制
	 */
	private Integer scorelimit;
	/**
	 * 使用的级别限制
	 */
	private Integer levellimit;
	/**
	 * 使用的范围限制
	 */
	private Integer scopelimit;
	/**
	 * 每个人使用的数量限制
	 */
	private Integer singlelimit;
	/**
	 * 每次使用的数量限制
	 */
	private Integer uselimit;
	/**
	 * 发布者
	 */
	private Integer publisherid;
	/**
	 * 活动发布者名字
	 */
	private String publishername;
	/**
	 * 所属的活动
	 */
	private Integer activityid;
	/**
	 * 优惠劵的类别如果是1则表示的是一个吧普通的优惠卷如果是2表示的是一个活动特定发放的优惠劵
	 */
	private Integer type;

}
