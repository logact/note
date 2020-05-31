package com.logact.cateen.service.page.impl;

import com.logact.cateen.dao.ActivityDao;
import com.logact.cateen.entity.ActivityEntity;
import com.logact.cateen.service.page.ActivityDetailService;
import com.logact.cateen.vo.activityDetail.ActivityDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;

/**
 * @author: logact
 * @date: Created in 2020/5/19 21:31
 * @description:
 */
@Service("ActivityDetail")
public class ActivityDetailServiceServiceImpl implements ActivityDetailService {
    @Autowired
    ActivityDao activityDao;
    @Override
    public ActivityDatas getDatas(Integer id) {
        ActivityEntity activityEntity = activityDao.selectById(id);

        ActivityDatas activityDatas = new ActivityDatas();
        activityDatas.setTitle(activityEntity.getTitle());
        activityDatas.setStatus(activityEntity.getTitle());

        activityDatas.setDesc(activityEntity.getDescr());
        activityDatas.setSimpleDesc(activityEntity.getSimpledesc());
        activityDatas.setId(activityEntity.getId());
        DateFormat df = DateFormat.getDateInstance();
        activityDatas.setEndTime(df.format(activityEntity.getEndtime()));
        activityDatas.setStartTime(df.format(activityEntity.getStarttime()));
        activityDatas.setImgSrc(activityEntity.getImgsrc().split(",")[0]);
        activityDatas.setInitiator(activityEntity.getInitiator());
        return activityDatas;
    }
}
