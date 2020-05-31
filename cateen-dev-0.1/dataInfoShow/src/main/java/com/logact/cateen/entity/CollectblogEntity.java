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
@TableName("collectblog")
public class CollectblogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *  主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 被收藏的博客的id
	 */
	private Integer blogid;
	/**
	 * 收藏的blog name
	 */
	private String blogname;
	/**
	 * 收藏的时间
	 */
	private Date collectdate;
	private Integer userId;

}
