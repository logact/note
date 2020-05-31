package com.logact.cateen.vo.activityDetail;

import lombok.Data;

/**
 * @author: logact
 * @date: Created in 2020/5/19 21:28
 * @description:
 */
@Data
public class ActivityDatas {
    Integer id;
    String title;
    String imgSrc;
    String status;
    String startTime;
    String endTime;
    String initiator;
    String simpleDesc;
    String desc;
}
