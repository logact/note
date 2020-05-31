package com.logact.cateen.controller.pageController;

import com.logact.cateen.service.page.ReviewDetailService;
import com.logact.cateen.service.page.ReviewService;
import com.logact.cateen.vo.reviewDetail.ReviewDetailDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: logact
 * @date: Created in 2020/5/19 14:55
 * @description:
 */
@RestController
@RequestMapping("/reviewDetail")
public class ReviewDetailController {
    @Autowired
    ReviewDetailService reviewDetailService;
    @RequestMapping("/datas")
    public ReviewDetailDatas getDatas(Integer id){
       return  reviewDetailService.getDatas(id);
    }
}
