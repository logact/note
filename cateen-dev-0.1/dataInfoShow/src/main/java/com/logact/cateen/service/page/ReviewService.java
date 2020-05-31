package com.logact.cateen.service.page;

import com.logact.cateen.vo.review.Review;
import com.logact.cateen.vo.review.ReviewDatas;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 13:07
 * @description:
 */
public interface  ReviewService {
    ReviewDatas getDatas(Integer foodId);

}
