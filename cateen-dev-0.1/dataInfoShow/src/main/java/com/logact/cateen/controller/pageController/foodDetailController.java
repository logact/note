package com.logact.cateen.controller.pageController;

import com.logact.cateen.dao.CollectfoodDao;
import com.logact.cateen.dao.FoodlabelDao;
import com.logact.cateen.entity.CollectfoodEntity;
import com.logact.cateen.entity.FoodEntity;
import com.logact.cateen.service.CollectfoodService;
import com.logact.cateen.service.FoodService;
import com.logact.cateen.service.page.FoodDetailService;
import com.logact.cateen.vo.foodDetail.Datas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: logact
 * @date: Created in 2020/5/18 19:11
 * @description:
 */
@RestController
@RequestMapping("/foodDetail")
public class foodDetailController {
    @Autowired
    FoodService foodService;
    @Autowired
    FoodDetailService foodDetailService;
    @Autowired
    CollectfoodService collectfoodService;
    @RequestMapping("/datas")
    public Datas getDatas(Integer userId, Integer foodId){
        return foodDetailService.getDatas(userId,foodId);
    }
    @RequestMapping("/collect")
    public boolean collect(Integer userId, Integer foodId){
        CollectfoodEntity collectfoodEntity = new CollectfoodEntity();
        collectfoodEntity.setUserid(userId);
        collectfoodEntity.setFoodid(foodId);
        collectfoodEntity.setCollectdate(new Date());
        collectfoodEntity.setFoodname(foodService.getById(foodId).getName());
        collectfoodService.save(collectfoodEntity);
        FoodEntity food = foodService.getById(foodId);
        food.setCollectnum(food.getCollectnum()+1);
        foodService.update(food,null);
        return true;
    }
    @RequestMapping("/unCollect")
    public boolean unCollect(Integer userId, Integer foodId){
        Map condition = new HashMap();
        condition.put("userId",userId);
        condition.put("foodId",foodId);
        collectfoodService.removeByMap(condition);
        FoodEntity food = foodService.getById(foodId);
        food.setCollectnum(food.getCollectnum()-1);
        foodService.update(food,null);
        return true;
    }
}
