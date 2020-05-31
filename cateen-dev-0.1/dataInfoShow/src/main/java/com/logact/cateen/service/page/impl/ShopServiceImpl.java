package com.logact.cateen.service.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.dao.CollectshopDao;
import com.logact.cateen.dao.ShopDao;
import com.logact.cateen.entity.CollectshopEntity;
import com.logact.cateen.entity.ShopEntity;
import com.logact.cateen.service.page.ShopService;
import com.logact.cateen.vo.shop.ShopDatas;
import com.logact.cateen.vo.shop.ShopInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 23:03
 * @description:
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    ShopDao shopDao;
    @Autowired
    CollectshopDao collectshopDao;
    @Override
    public ShopDatas getDatas(Integer id,Integer userId) {
        ShopDatas shopDatas = new ShopDatas();
        ShopInfo shopInfo = new ShopInfo();
        ShopEntity shopEntity = shopDao.selectById(id);
        shopInfo.setCollectNum(shopEntity.getCollectednumber());
        shopInfo.setDesc(shopEntity.getDescr());
        shopInfo.setId(shopEntity.getId());
        shopInfo.setLocation(shopEntity.getLocation());
        List<String> images = Arrays.asList(shopEntity.getImages().split(","));
        shopInfo.setImages(images);
        shopInfo.setRate(shopEntity.getRate());
        shopInfo.setView(shopEntity.getView().toString());
        shopInfo.setName(shopEntity.getName());

        List<CollectshopEntity> xx = collectshopDao.selectList(new QueryWrapper<CollectshopEntity>().eq("userId", userId));
        if(xx==null||xx.size()==0){
            shopInfo.setCollected(false);

        }else{
            shopInfo.setCollected(true);
        }

//这里两条数据使用mongoDb中的数据
//        shopInfo.setRecommendMenu();
//        shopInfo.setToDayMenu();
        shopDatas.setShopInfo(shopInfo);

        return shopDatas;
    }
}
