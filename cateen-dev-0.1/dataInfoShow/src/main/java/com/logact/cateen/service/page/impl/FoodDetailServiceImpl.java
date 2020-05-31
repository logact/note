package com.logact.cateen.service.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.dao.*;
import com.logact.cateen.entity.CollectfoodEntity;
import com.logact.cateen.entity.FoodEntity;
import com.logact.cateen.entity.LablefoodEntity;
import com.logact.cateen.service.page.FoodDetailService;
import com.logact.cateen.vo.foodDetail.Datas;
import com.logact.cateen.vo.foodDetail.RecommendFood;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;

/**
 * @author: logact
 * @date: Created in 2020/5/18 17:36
 * @description:
 */
@Service("FoodDetailService")
@Slf4j
public class FoodDetailServiceImpl implements FoodDetailService {
    @Autowired
    FoodDao foodDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    LablefoodDao lablefoodDao;
    @Autowired
    FoodlabelDao foodlabelDao;
    @Autowired
    CollectfoodDao collectfoodDao;
    @Override
    public Datas getDatas(Integer userId,Integer foodId) {
        Datas datas = new Datas();
        FoodEntity foodEntity = foodDao.selectById(foodId);

        datas.setCollectNum(foodEntity.getCollectnum());
        datas.setDesc(foodEntity.getDescr());
        datas.setId(foodEntity.getId());
        datas.setShopId(foodEntity.getShopid());
        datas.setShop(foodEntity.getShop());
        String location = shopDao.selectById(foodEntity.getShopid()).getLocation();
        datas.setLocation(location);
        datas.setName(foodEntity.getName());
        datas.setPrice(foodEntity.getPrice());
        DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日
        Date publishdate = foodEntity.getPublishdate();
        datas.setPublishDate(df.format(publishdate));
        datas.setScore(foodEntity.getScore());
        Date upadatedate = foodEntity.getUpadatedate();
        datas.setUpdateDate(df.format(upadatedate));

        Map<String, Object> condition=new HashMap<String, Object>();
        condition.put("foodId",foodId);
        condition.put("userId",userId);
        List<CollectfoodEntity> collectfoodEntities = collectfoodDao.selectByMap(condition);
        log.info("System.out.println(collectfoodEntities);");
        log.info(collectfoodEntities.toString());
        log.info(collectfoodEntities.toString());
        log.info(collectfoodEntities.toString());
        log.info(collectfoodEntities.toString());
        if(collectfoodEntities!=null&&collectfoodEntities.size()!=0){
            datas.setCollected(true);
        }else{
            datas.setCollected(false);
        }
        log.info("XX"+datas.toString());
        log.info("XX"+datas.toString());
        log.info("XX"+collectfoodEntities.toString());
//        复杂对象注入
        List<String> types = new ArrayList();
        QueryWrapper wrapper1= new QueryWrapper();
        wrapper1.eq("foodId",foodEntity.getId());
        List<LablefoodEntity> labels= lablefoodDao.selectList(wrapper1);
        for (LablefoodEntity label : labels) {
            types.add(foodlabelDao.selectById(label.getLabelid()).getName());
        }
        datas.setTypes(types);
        List<RecommendFood> recommendFoods=new ArrayList<RecommendFood>();
//        添加相同的类型的食物这样添加会有重复
        Set<Integer> set =new HashSet<Integer>();
        set.add(foodId);
        for (LablefoodEntity label : labels) {
            QueryWrapper wrapper2 = new QueryWrapper();
            wrapper2.eq("labelId", label.getLabelid());
            List<LablefoodEntity> list = lablefoodDao.selectList(wrapper2);
            for (LablefoodEntity lablefoodEntity : list) {
                FoodEntity foodEntity1 = foodDao.selectById(lablefoodEntity.getFoodid());
                if (foodEntity1 == null) {
                    continue;
                }
                RecommendFood recommendFood = new RecommendFood();
                recommendFood.setId(foodEntity1.getId());
                recommendFood.setImgSrc(foodEntity1.getImages().split(",")[0]);
                recommendFood.setShop(foodEntity1.getShop());
                recommendFood.setName(foodEntity1.getName());
                if(!set.contains(recommendFood.getId())){
                    recommendFoods.add(recommendFood);
                    set.add(recommendFood.getId());
                }
            }
        }
        datas.setRecommendFood(recommendFoods);

        List<String> imgs= Arrays.asList(foodEntity.getImages().split(","));
        datas.setFoodDetailImgs(imgs);
        List<String > ingredients= Arrays.asList(foodEntity.getIngredients().split(","));
        datas.setIngredients(ingredients);
        return datas;
    }
}
