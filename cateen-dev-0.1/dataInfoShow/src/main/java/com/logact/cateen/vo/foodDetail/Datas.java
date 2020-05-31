package com.logact.cateen.vo.foodDetail;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/18 17:10
 * @description:
 */
@Data
public class Datas {
    Integer id;
    String shopId;
    boolean isCollected;
    String publishDate;
    String updateDate;
    String name;
    String shop;
    String location;
    String price;
    List<String> foodDetailImgs;
    Integer score;
    Integer collectNum;
    String desc;
    List<String> types;
    List<RecommendFood> recommendFood;
    List<String>ingredients;

}
