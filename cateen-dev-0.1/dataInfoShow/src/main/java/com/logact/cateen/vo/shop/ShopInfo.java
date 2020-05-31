package com.logact.cateen.vo.shop;

import lombok.Data;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 22:58
 * @description:
 */
@Data
public class ShopInfo {
    Integer collectNum;
    Integer id;
    String desc;
    String name;
    String location;
    String view;
    Integer rate;
    List<Menu> toDayMenu;
    List<Menu>recommendMenu;
    List<String> images;
    boolean collected;
}
