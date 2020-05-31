package com.logact.cateen.vo.activity;

import lombok.Data;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 17:31
 * @description:
 */
@Data
public class ActivityDatas {
    List<Activity> startActivityList;
    List<Activity> endActivityList;
    List<Activity>readyActivityList;
}
