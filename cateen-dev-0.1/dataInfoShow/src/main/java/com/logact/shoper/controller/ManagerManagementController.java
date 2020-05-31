package com.logact.shoper.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.entity.ActivityEntity;
import com.logact.cateen.entity.FoodEntity;
import com.logact.cateen.entity.ShopEntity;
import com.logact.cateen.entity.ShoperEntity;
import com.logact.cateen.service.ActivityService;
import com.logact.cateen.service.FoodService;
import com.logact.cateen.service.ShopService;
import com.logact.cateen.service.ShoperService;
import com.logact.cateen.vo.activity.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: logact
 * @date: Created in 2020/5/29 15:37
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("managerManagement")
public class ManagerManagementController {
    @Autowired
    FoodService foodService;
    @Autowired
    ShopService shopService;
    @Autowired
    ActivityService activityService;
    @Autowired
    ShoperService shoperService;
    @RequestMapping("test")
    public String test(){
        return "test success";
    }
    @RequestMapping("check")
    public String checked(String type,Integer id){
        log.info(type);
        log.info(id.toString());
        if(type.equals("food")){
            FoodEntity food = foodService.getById(id);
            food.setChecked(2);
            QueryWrapper condition = new QueryWrapper();
            condition.eq("id", id);
            foodService.update(food, condition);
        }else if(type.equals("shop")){
            ShopEntity shop = shopService.getById(id);
            shop.setChecked(2);
            QueryWrapper condition = new QueryWrapper();
            condition.eq("id", id);
            shopService.update(shop, condition);
        }else if(type.equals("activity")){
            ActivityEntity activity = activityService.getById(id);
            activity.setChecked(2);
            QueryWrapper condition = new QueryWrapper();
            condition.eq("id", id);
            activityService.update(activity, condition);
        }else if(type.equals("shoper")){
            ShoperEntity shoper = shoperService.getById(id);
            shoper.setCheck(2);
            QueryWrapper condition = new QueryWrapper();
            condition.eq("id", id);
            shoperService.update(shoper,condition);
        }
        return "success";
    }
    @RequestMapping("uncheck")
    public String unCheck(String type,Integer id){
        if(type.equals("food")){
            foodService.removeById(id);
        }else if(type.equals("shop")){
            shopService.removeById(id);
        }else if(type.equals("activity")){
            activityService.removeById(id);
        }else if(type.equals("shoper")){
            shoperService.removeById(id);
        }
        return "success";
    }
}
