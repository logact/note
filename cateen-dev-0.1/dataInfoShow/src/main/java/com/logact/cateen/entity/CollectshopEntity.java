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
@TableName("collectshop")
public class CollectshopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 被收藏的店铺的id
	 */
	private Integer shopid;
	/**
	 * 被收藏的店铺的名字
	 */
	private String shopname;
	/**
	 * 收藏的时间
	 */
	private Date collectdate;
	private Integer userid;

}
