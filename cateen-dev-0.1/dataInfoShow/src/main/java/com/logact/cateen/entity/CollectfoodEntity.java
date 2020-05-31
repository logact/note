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
 * @date 2020-05-18 18:15:48
 */
@Data
@TableName("collectfood")
public class CollectfoodEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type= IdType.AUTO)
	private Integer id;
	/**
	 * 收藏的食品id
	 */
	private Integer foodid;
	/**
	 * 收藏的食物名
	 */
	private String foodname;
	/**
	 * 收藏的时间
	 */
	private Date collectdate;
	private Integer userid;

}
