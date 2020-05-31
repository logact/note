package com.logact.shoper.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/25 12:21
 * @description:
 */
@Data
public class MenuDetail {
    String date;
    List<SimpleFood> foods;
 }
