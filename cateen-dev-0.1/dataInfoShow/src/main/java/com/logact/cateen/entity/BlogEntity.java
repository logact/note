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
@TableName("blog")
public class BlogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 用户的主键
	 */
	private Integer authorid;
	/**
	 * blog的图片
	 */
	private String img;
	/**
	 * 发布的日期
	 */
	private Date publish;
	/**
	 * blog 的内容
	 */
	private String content;
	/**
	 * 收藏的人数
	 */
	private Integer loveCount;
	/**
	 * 浏览9的人数
	 */
	private Integer attentionCount;
	private Integer check;

}
