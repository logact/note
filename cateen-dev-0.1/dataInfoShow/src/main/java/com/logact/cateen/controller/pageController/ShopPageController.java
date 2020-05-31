package com.logact.cateen.controller.pageController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.dao.CollectshopDao;
import com.logact.cateen.entity.CollectshopEntity;
import com.logact.cateen.entity.ShopEntity;
import com.logact.cateen.service.CollectshopService;
import com.logact.cateen.service.page.ShopService;
import com.logact.cateen.vo.shop.ShopDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/27 17:20
 * @description:
 */
@RestController
@RequestMapping("/shop")
public class ShopPageController {
    @Autowired
    ShopService shopService;
    @Autowired
    com.logact.cateen.service.ShopService shopService1;
    @Autowired
    CollectshopService collectshopService;
    @GetMapping("/info")
    public ShopDatas getShop(Integer id,Integer userId){
        return shopService.getDatas(id,userId);
    }
    @RequestMapping("/collect")
    public void handleCollect(Integer shopId, Integer userId){
        QueryWrapper condition = new QueryWrapper();
        condition.eq("shopId", shopId);
        condition.eq("userId", userId);
        List list = collectshopService.list(condition);
        if(list==null||list.size()==0){
            CollectshopEntity collectshopEntity = new CollectshopEntity();
            collectshopEntity.setCollectdate(new Date());
            collectshopEntity.setShopid(shopId);
            collectshopEntity.setUserid(userId);
            ShopEntity shop = shopService1.getById(shopId);
            collectshopEntity.setShopname(shop.getName());
            collectshopService.save(collectshopEntity);

            shop.setCollectednumber(shop.getCollectednumber()+1);
            shopService1.update(shop,null);

        }else{
            QueryWrapper condition1 = new QueryWrapper();
            condition1.eq("userId", userId);
            condition1.eq("shopId", shopId);
            collectshopService.remove(condition1);
            ShopEntity shopEntity = shopService1.getById(shopId);
            shopEntity.setCollectednumber(shopEntity.getCollectednumber()-1);
            shopService1.update(shopEntity,null);
        }

    }
}
