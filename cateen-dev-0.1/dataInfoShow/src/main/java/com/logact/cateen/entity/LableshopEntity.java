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
@TableName("lableshop")
public class LableshopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 标签id 
	 */
	private Integer labelid;
	/**
	 * 商店id
	 */
	private Integer shopid;

}
