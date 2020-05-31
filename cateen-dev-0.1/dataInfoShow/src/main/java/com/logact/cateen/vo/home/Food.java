package com.logact.cateen.vo.home;

import lombok.Data;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/18 13:50
 * @description:
 */
@Data
public class Food {
    Integer id;
    String name;
    String shop;
    String desc;
    String location;
    String price;
    Integer score;
    List<String> tags;
    String img;
    Integer check;
}
