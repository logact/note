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
@TableName("shop")
public class ShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 收藏的数量
	 */
	private Integer collectednumber;
	/**
	 * 商店的描述
	 */
	private String descr;
	/**
	 * 商店的名称
	 */
	private String name;
	/**
	 * 商店的位置
	 */
	private String location;
	/**
	 * 被浏览的数量
	 */
	private Integer view;
	/**
	 * 评分
	 */
	private Integer rate;
	/**
	 * 商店的宣传图用逗号隔开
	 */
	private String images;
	/**
	 *
	 */
	private Integer shoperid;
	private Integer checked;
}
