package com.logact.cateen.controller.pageController;

import com.logact.cateen.filter.Check;
import com.logact.cateen.service.page.HomeService;
import com.logact.cateen.vo.home.Food;
import com.logact.cateen.vo.home.HomeDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/18 14:46
 * @description:
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService ;
    @GetMapping("/datas")
    public HomeDatas datas(Integer userId){

        HomeDatas datas = homeService.getDatas(userId);
        List<Food> foodList = datas.getFoodList();
        Check.check(Food.class, foodList);
        return datas;

    }
}
