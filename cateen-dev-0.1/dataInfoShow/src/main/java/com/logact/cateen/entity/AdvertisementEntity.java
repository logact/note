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
@TableName("advertisement")
public class AdvertisementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 图片的地址用逗号隔开
	 */
	private String imgs;
	/**
	 * 广告连接地址
	 */
	private String url;

}
