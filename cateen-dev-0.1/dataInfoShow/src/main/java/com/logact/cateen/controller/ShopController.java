package com.logact.cateen.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.logact.cateen.filter.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logact.cateen.entity.ShopEntity;
import com.logact.cateen.service.ShopService;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.R;



/**
 *
 *
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 11:11:40
 */
@RestController
@RequestMapping("cateen/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopService.queryPage(params);
//        Check.check(ShopEntity.class, page.getList());
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ShopEntity shop = shopService.getById(id);

        return R.ok().put("shop", shop);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShopEntity shop){
        System.out.println(shop);
		shopService.save(shop);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShopEntity shop){
		shopService.updateById(shop);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		shopService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
