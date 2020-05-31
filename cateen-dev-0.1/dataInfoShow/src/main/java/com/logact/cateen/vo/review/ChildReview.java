package com.logact.cateen.vo.review;

import lombok.Data;

/**
 * @author: logact
 * @date: Created in 2020/5/19 13:10
 * @description:
 */
@Data
public class ChildReview {
    Integer id;
    String from;
    String to;
    Integer toId;
    String content;
}
