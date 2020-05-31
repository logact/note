package com.logact.cateen.controller.pageController;

import com.logact.cateen.filter.Check;
import com.logact.cateen.service.page.ShopesService;
import com.logact.cateen.vo.shopes.Shop;
import com.logact.cateen.vo.shopes.ShopesDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 22:42
 * @description:
 */
@RestController
@RequestMapping("shopes")
public class ShopesController {
    @Autowired
    ShopesService shopesService;
    @RequestMapping("datas")
    public ShopesDatas getDatas()
    {
        ShopesDatas datas = shopesService.getDatas();
        List<Shop> shopList = datas.getShopList();
        Check.check(Shop.class, shopList);
        return datas;
    }
}
