package com.logact.cateen.service.impl;

import com.logact.cateen.entity.MenuEntity;
import com.logact.cateen.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/25 10:44
 * @description:
 */
@Service("MenuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Override
    public List<MenuEntity> listAll() {
        List<MenuEntity> menus = mongoTemplate.findAll(MenuEntity.class,"menu");
        return menus;
    }
    @Override
    public MenuEntity getByID(String id) {
        MenuEntity menuEntity = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), MenuEntity.class, "menu");
        return menuEntity;
    }

    @Override
    public List<MenuEntity> getByShopId(Integer shopId)
    {
        List<MenuEntity> menuEntity = mongoTemplate.find(new Query(Criteria.where("shopId").is(shopId)),MenuEntity.class,"menu");
        return  menuEntity;
    }

    @Override
    public boolean save(MenuEntity menuEntity) {
        System.out.println(mongoTemplate.insert(menuEntity, "menu"));
        return true;
    }
}
