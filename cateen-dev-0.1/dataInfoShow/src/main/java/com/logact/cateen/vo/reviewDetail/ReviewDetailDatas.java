package com.logact.cateen.vo.reviewDetail;

import lombok.Data;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 14:56
 * @description:
 */
@Data
public class ReviewDetailDatas {
    Review review;
    List<ChildReview> childReviews ;
}
