package com.logact.cateen.controller;

import com.logact.cateen.entity.MenuEntity;
import com.logact.cateen.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/25 10:55
 * @description:
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @GetMapping("/list")
    public List<MenuEntity> list(){
        return menuService.listAll();
    }
    @GetMapping("/byId")
    public MenuEntity getById(String id){
        return menuService.getByID(id);
    }
    @GetMapping("/byShop")
    public List<MenuEntity> getByShop(Integer id){
        return menuService.getByShopId(id);
    }
}
