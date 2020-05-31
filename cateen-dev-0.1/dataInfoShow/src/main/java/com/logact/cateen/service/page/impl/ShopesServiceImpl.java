package com.logact.cateen.service.page.impl;

import com.logact.cateen.dao.ShopDao;
import com.logact.cateen.dao.ShoperDao;
import com.logact.cateen.entity.ShopEntity;
import com.logact.cateen.service.page.ShopesService;
import com.logact.cateen.vo.shopes.Shop;
import com.logact.cateen.vo.shopes.ShopesDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 22:34
 * @description:
 */
@Service("ShopesService")
public class ShopesServiceImpl implements ShopesService {
    @Autowired
    ShopDao shopDao;
    @Override
    public ShopesDatas getDatas() {
        ShopesDatas res = new ShopesDatas();
        List<ShopEntity> shopEntities = shopDao.selectList(null);
        List<Shop> shops=new ArrayList<Shop>();
        for (ShopEntity entity : shopEntities) {
            Shop shop = new Shop();
            shop.setId(entity.getId());
            shop.setDesc(entity.getDescr());
            String[] images= entity.getImages().split(",");
            shop.setImg(images[0]);
            shop.setLocation(entity.getLocation());
            shop.setSocre(entity.getRate());
            shop.setName(entity.getName());
            shop.setCheck(entity.getChecked());
            shops.add(shop);
        }
        res.setShopList(shops);
        return res;
    }
}
