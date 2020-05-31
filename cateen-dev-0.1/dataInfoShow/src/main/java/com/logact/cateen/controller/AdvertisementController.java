package com.logact.cateen.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logact.cateen.entity.AdvertisementEntity;
import com.logact.cateen.service.AdvertisementService;
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
@RequestMapping("cateen/advertisement")
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = advertisementService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		AdvertisementEntity advertisement = advertisementService.getById(id);

        return R.ok().put("advertisement", advertisement);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AdvertisementEntity advertisement){
		advertisementService.save(advertisement);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AdvertisementEntity advertisement){
		advertisementService.updateById(advertisement);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		advertisementService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
