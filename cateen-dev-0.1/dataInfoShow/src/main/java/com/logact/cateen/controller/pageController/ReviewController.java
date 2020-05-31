package com.logact.cateen.controller.pageController;

import com.logact.cateen.service.page.ReviewService;
import com.logact.cateen.vo.review.ReviewDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: logact
 * @date: Created in 2020/5/19 13:06
 * @description:
 */
@RestController
@RequestMapping("review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @RequestMapping("datas")
    public ReviewDatas getDatas(Integer id){
        return reviewService.getDatas(id);
    }

}
