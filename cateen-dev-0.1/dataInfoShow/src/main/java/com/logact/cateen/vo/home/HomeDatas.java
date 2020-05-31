package com.logact.cateen.vo.home;

import lombok.Data;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/18 13:52
 * @description:
 */
@Data
public class HomeDatas {
    List<Advertisement> advertisement;
    List<Food> foodList;
}
