package com.logact.shoper.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author: logact
 * @date: Created in 2020/5/26 12:20
 * @description:
 */
@Data
public class ActivityR {
    private Integer shoperId;
    private  String name;
    private Date date1;
    private Date date2;
    private  String simpleDesc;
    private  String desc;
    private  String pics;
}
