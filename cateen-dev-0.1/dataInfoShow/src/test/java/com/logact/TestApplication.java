package com.logact;

import com.logact.cateen.entity.MenuEntity;
import com.logact.cateen.service.MenuService;
import com.logact.common.utils.Uuid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/25 10:30
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {
    @Autowired
    MongoTemplate mongoTemplate;
    @Test
    public void test(){
        MenuEntity menu= new MenuEntity();
        menu.setId(Uuid.generate());
        menu.setDate(new Date());
        menu.setFoodIds("1,2,3");
        menu.setShopId(1);
        mongoTemplate.insert(menu,"menu");
    }
    @Test
    public void find() throws Exception {
        MenuEntity menuEntity = new MenuEntity();
        List<MenuEntity> menus = mongoTemplate.findAll(MenuEntity.class);
        menus.forEach(u -> {
            System.out.println(u.toString());
        });
    }
    @Autowired
    MenuService menuService;
    @Test
    public void testMenu(){
        System.out.println(menuService.listAll());
    }

}
