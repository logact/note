package com.logact.cateen.service.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.dao.ActivityDao;
import com.logact.cateen.entity.ActivityEntity;
import com.logact.cateen.service.page.ActivityService;
import com.logact.cateen.vo.activity.Activity;
import com.logact.cateen.vo.activity.ActivityDatas;
import com.logact.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 17:40
 * @description:
 */
@Service("ActivityService")
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityDao activityDao;
    @Override
    public ActivityDatas getDatas() {
        ActivityDatas activityDatas = new ActivityDatas();
        List<Activity> startActivityList = new ArrayList<Activity>();
        List<Activity> readyActivityList = new ArrayList<Activity>();
        List<Activity> endActivityList = new ArrayList<Activity>();

        QueryWrapper condition1 = new QueryWrapper();

        condition1.gt("startTime", DateUtil.DateToString(new Date()));
        List<ActivityEntity> activityEntities1 = activityDao.selectList(condition1);
        for (ActivityEntity activityEntity : activityEntities1) {
            Activity activity = new Activity();
            activity.setId(activityEntity.getId());
            activity.setDesc(activityEntity.getDescr());
            activity.setImgSrc(activityEntity.getImgsrc().split(",")[0]);
            activity.setStatus(activityEntity.getStatus());
            activity.setInitiator(activityEntity.getInitiator());
            activity.setCheck(activityEntity.getChecked());
            activity.setTitle(activityEntity.getTitle());
            DateFormat df = DateFormat.getDateInstance();
            activity.setStartTime( df.format(activityEntity.getStarttime()));
            activity.setEndTime((df.format(activityEntity.getEndtime())));
            readyActivityList.add(activity);
        }
        activityDatas.setReadyActivityList(readyActivityList);




        QueryWrapper condition2 = new QueryWrapper();
        condition2.le("startTime", DateUtil.DateToString(new Date()));
        condition2.ge("endTime",DateUtil.DateToString(new Date()));
        List<ActivityEntity> activityEntities2 = activityDao.selectList(condition2);
        for (ActivityEntity activityEntity : activityEntities2) {
            Activity activity = new Activity();
            activity.setId(activityEntity.getId());
            activity.setDesc(activityEntity.getDescr());
            activity.setImgSrc(activityEntity.getImgsrc());
            activity.setStatus(activityEntity.getStatus());
            activity.setInitiator(activityEntity.getInitiator());
            activity.setTitle(activityEntity.getTitle());
            activity.setCheck(activityEntity.getChecked());
            DateFormat df = DateFormat.getDateInstance();
            activity.setStartTime( df.format(activityEntity.getStarttime()));
            activity.setEndTime((df.format(activityEntity.getEndtime())));
            startActivityList.add(activity);
        }
        activityDatas.setStartActivityList(startActivityList);


        QueryWrapper condition3 = new QueryWrapper();
        condition3.lt("endTime", DateUtil.DateToString(new Date()));
        List<ActivityEntity> activityEntities3 = activityDao.selectList(condition3);
        for (ActivityEntity activityEntity : activityEntities3) {
            Activity activity = new Activity();
            activity.setId(activityEntity.getId());
            activity.setDesc(activityEntity.getDescr());
            activity.setImgSrc(activityEntity.getImgsrc());
            activity.setStatus(activityEntity.getStatus());
            activity.setInitiator(activityEntity.getInitiator());
            activity.setTitle(activityEntity.getTitle());
            activity.setCheck(activityEntity.getChecked());
            DateFormat df = DateFormat.getDateInstance();
            activity.setStartTime( df.format(activityEntity.getStarttime()));
            activity.setEndTime((df.format(activityEntity.getEndtime())));
            endActivityList.add(activity);
        }
        activityDatas.setEndActivityList(endActivityList);
        return activityDatas;
    }
}
