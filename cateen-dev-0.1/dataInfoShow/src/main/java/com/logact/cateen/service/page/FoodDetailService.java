package com.logact.cateen.service.page;

import com.logact.cateen.vo.foodDetail.Datas;

/**
 * @author: logact
 * @date: Created in 2020/5/18 17:35
 * @description:
 */
public interface FoodDetailService {
    Datas getDatas(Integer userId,Integer foodId);

}
