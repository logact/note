package com.logact.cateen.service.page;

import com.logact.cateen.vo.activity.ActivityDatas;
import com.logact.common.utils.DateUtil;

import java.util.Date;

/**
 * @author: logact
 * @date: Created in 2020/5/19 17:39
 * @description:
 */
public interface ActivityService {
    ActivityDatas getDatas();

    public static void main(String[] args) {
        System.out.println(DateUtil.isExpired(new Date()));
    }
}
