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
@TableName("shoplabel")
public class ShoplabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 标签名
	 */
	private String name;
	/**
	 * 父标签
	 */
	private Integer plabelid;
	/**
	 * 是否显示在搜索框中
	 */
	private Boolean showinsearch;

}
