package com.logact.cateen.controller.pageController;

import com.logact.cateen.service.page.ActivityDetailService;
import com.logact.cateen.vo.activityDetail.ActivityDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: logact
 * @date: Created in 2020/5/19 21:26
 * @description:
 */
@RestController
@RequestMapping("/activityDetail")
public class ActivityDetailController {
    @Autowired
    ActivityDetailService activityDetailService;
    @RequestMapping("/datas")
    public ActivityDatas getDatas(Integer id){
        ActivityDatas datas = activityDetailService.getDatas(id);
        return datas;

    }
}
