package com.logact.cateen.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logact.cateen.entity.FoodlabelEntity;
import com.logact.cateen.service.FoodlabelService;
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
@RequestMapping("cateen/foodlabel")
public class FoodlabelController {
    @Autowired
    private FoodlabelService foodlabelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = foodlabelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		FoodlabelEntity foodlabel = foodlabelService.getById(id);

        return R.ok().put("foodlabel", foodlabel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FoodlabelEntity foodlabel){
		foodlabelService.save(foodlabel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FoodlabelEntity foodlabel){
		foodlabelService.updateById(foodlabel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		foodlabelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
