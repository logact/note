package com.logact.cateen.vo.activity;

import lombok.Data;

/**
 * @author: logact
 * @date: Created in 2020/5/19 17:34
 * @description:
 */
@Data
public class Activity {
    Integer id;
    String title;
    String imgSrc;
    String desc;
    String initiator;
    String startTime;
    String endTime;
    String status;
    Integer check;
}
