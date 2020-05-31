package com.logact.cateen.service.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.dao.*;
import com.logact.cateen.entity.*;
import com.logact.cateen.service.page.HomeService;
import com.logact.cateen.vo.home.Advertisement;
import com.logact.cateen.vo.home.Food;
import com.logact.cateen.vo.home.HomeDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/18 13:55
 * @description:
 */
@Service("homeService")
public class homeServiceImpl implements HomeService {
    @Autowired
    AdvertisementDao advertisementDao;
    @Autowired
    FoodDao foodDao;
    @Autowired
    LablefoodDao labelfoodDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    FoodlabelDao foodlabelDao;
    @Autowired
    UserDao userDao;

    @Override
    public HomeDatas getDatas(Integer userId) {
        List<Advertisement> advertisement= new ArrayList<Advertisement>();
        List<Food> foodList = new ArrayList();

        List<AdvertisementEntity> advertisementEntities = advertisementDao.selectList(null);
        for (AdvertisementEntity advertisementEntity : advertisementEntities) {
            Advertisement advertisementOne = new Advertisement();
            advertisementOne.setId(advertisementEntity.getId());
            advertisementOne.setUrl(advertisementEntity.getUrl());
            advertisementOne.setImgSrc(advertisementEntity.getImgs());
            advertisement.add(advertisementOne);
        }


        List<FoodEntity> foodEntities = foodDao.selectList(new QueryWrapper<FoodEntity>().orderByDesc("score"));

        System.out.println(foodEntities);
        for(FoodEntity e: foodEntities) {
            Food foodOne = new Food();
            foodOne.setId(e.getId());
            foodOne.setDesc(e.getDescr());
            foodOne.setName(e.getName());
            foodOne.setPrice(e.getPrice());
            foodOne.setScore(e.getScore());
            foodOne.setShop(e.getShop());
            foodOne.setCheck(e.getChecked());

            QueryWrapper wrapper1= new QueryWrapper();
            wrapper1.eq("foodId",e.getId());
            List<LablefoodEntity> labels= labelfoodDao.selectList(wrapper1);

            List<String> tags= new ArrayList<String>();
            for (LablefoodEntity label : labels) {
                FoodlabelEntity foodlabelEntity = foodlabelDao.selectById(label.getLabelid());
                tags.add(foodlabelEntity.getName());
            }
            foodOne.setTags(tags);
            String location=shopDao.selectById(e.getShopid()).getLocation();
            foodOne.setLocation(location);
            List<String> images = Arrays.asList(e.getImages().split(","));
            foodOne.setImg(images.get(0));
            foodList.add(foodOne);

        }
        HomeDatas datas=new HomeDatas();
        datas.setAdvertisement(advertisement);
        datas.setFoodList(foodList);
        return datas;
    }
}
