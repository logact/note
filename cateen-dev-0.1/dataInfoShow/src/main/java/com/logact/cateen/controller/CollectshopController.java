package com.logact.cateen.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logact.cateen.entity.CollectshopEntity;
import com.logact.cateen.service.CollectshopService;
import com.logact.common.utils.PageUtils;
import com.logact.common.utils.R;



/**
 * 
 *
 * @author logact
 * @email logact@qq.com
 * @date 2020-05-18 18:15:48
 */
@RestController
@RequestMapping("cateen/collectshop")
public class CollectshopController {
    @Autowired
    private CollectshopService collectshopService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = collectshopService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		CollectshopEntity collectshop = collectshopService.getById(id);

        return R.ok().put("collectshop", collectshop);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CollectshopEntity collectshop){
		collectshopService.save(collectshop);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CollectshopEntity collectshop){
		collectshopService.updateById(collectshop);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		collectshopService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
