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
@TableName("shopcomment")
public class ShopcommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 评论的内容给
	 */
	private String content;
	/**
	 * 评论所属的商店id
	 */
	private Integer shopid;
	/**
	 * 父评论id
	 */
	private Integer parentid;
	/**
	 * 发布者id
	 */
	private Integer frompublisherid;
	/**
	 * 回复对象id
	 */
	private Integer topublisherid;

}
