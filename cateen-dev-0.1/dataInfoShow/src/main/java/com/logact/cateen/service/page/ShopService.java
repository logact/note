package com.logact.cateen.service.page;

import com.logact.cateen.vo.shop.ShopDatas;

/**
 * @author: logact
 * @date: Created in 2020/5/19 23:03
 * @description:
 */
public interface ShopService {
    ShopDatas getDatas(Integer id,Integer userId);
}
