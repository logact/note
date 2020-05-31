package com.logact.cateen.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: logact
 * @date: Created in 2020/5/25 10:27
 * @description:
 */
@Data
public class MenuEntity {
    private String id;
    private int shopId;
    private Date date;
    private String foodIds;
}
