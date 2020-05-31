package com.logact.cateen.controller.pageController;

import com.logact.cateen.filter.Check;
import com.logact.cateen.service.page.ActivityService;
import com.logact.cateen.vo.activity.Activity;
import com.logact.cateen.vo.activity.ActivityDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 20:50
 * @description:
 */
@RestController
@RequestMapping("/activity")
public class ActivityPageController {
    @Autowired
    ActivityService activityService;
    @RequestMapping("/datas")
    public ActivityDatas getDatas(){
        ActivityDatas datas = activityService.getDatas();
        List<Activity> endActivityList = datas.getEndActivityList();
        List<Activity> readyActivityList = datas.getReadyActivityList();
        List<Activity> startActivityList = datas.getStartActivityList();
        Check.check(Activity.class, endActivityList);
        Check.check(Activity.class, startActivityList);
        Check.check(Activity.class, readyActivityList);
        return datas;
    }
}
