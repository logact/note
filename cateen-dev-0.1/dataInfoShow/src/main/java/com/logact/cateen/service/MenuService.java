package com.logact.cateen.service;

import com.logact.cateen.entity.MenuEntity;

import java.util.HashMap;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/25 10:42
 * @description:
 */
public interface MenuService {
    List<MenuEntity> listAll();
    MenuEntity getByID(String id);
    List<MenuEntity> getByShopId(Integer id);
    boolean save(MenuEntity menuEntity);
}
