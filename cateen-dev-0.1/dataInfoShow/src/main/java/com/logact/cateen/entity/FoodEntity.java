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
@TableName("food")
public class FoodEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId( type = IdType.AUTO)
	private Integer id;
	/**
	 * 品名
	 */
	private String name;
	/**
	 * 归属的店的主键
	 */
	private String shopid;
	/**
	 * 归属的点名 冗余
	 */
	private String shop;
	/**
	 *  价格
	 */
	private String price;
	/**
	 * 分数
	 */
	private Integer score;
	/**
	 * 被收藏数
	 */
	private Integer collectnum;
	/**
	 *  菜品描述
	 */
	private String descr;
	/**
	 * 发布日期
	 */
	private Date publishdate;
	/**
	 * 更新日期
	 */
	private Date upadatedate;
	/**
	 * 菜品被浏览的数量
	 */
	private Integer view;
	/**
	 * 菜品的宣传图用逗号隔开
	 */
	private String images;
	/**
	 * 菜品的配料用逗号隔开
	 */
	private String ingredients;
	private Integer checked;
}
