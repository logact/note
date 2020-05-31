package com.logact.cateen.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logact.cateen.entity.ShopcommentEntity;
import com.logact.cateen.service.ShopcommentService;
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
@RequestMapping("cateen/shopcomment")
public class ShopcommentController {
    @Autowired
    private ShopcommentService shopcommentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopcommentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ShopcommentEntity shopcomment = shopcommentService.getById(id);

        return R.ok().put("shopcomment", shopcomment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ShopcommentEntity shopcomment){
		shopcommentService.save(shopcomment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ShopcommentEntity shopcomment){
		shopcommentService.updateById(shopcomment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		shopcommentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
