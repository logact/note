package com.logact.cateen.vo.review;

import lombok.Data;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 13:08
 * @description:
 */
@Data
public class Review {
    Integer id;
    String avatar;
    String author;
    Integer authorId;
    String content;
//    子评论共有多少条
    String sum;
    List<ChildReview> childReviews;

}
