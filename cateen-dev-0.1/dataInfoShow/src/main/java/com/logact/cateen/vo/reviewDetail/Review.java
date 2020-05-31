package com.logact.cateen.vo.reviewDetail;

import lombok.Data;

/**
 * @author: logact
 * @date: Created in 2020/5/19 14:57
 * @description:
 */
@Data
public class Review {
    Integer id;
    String avatar;
    String author;
    String date;
    String content;
}
