package com.logact.cateen.service.page.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.dao.FoodDao;
import com.logact.cateen.dao.ShopDao;
import com.logact.cateen.entity.FoodEntity;
import com.logact.cateen.entity.ShopEntity;
import com.logact.cateen.service.page.SearchService;
import com.logact.cateen.vo.search.RelatedOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/19 16:19
 * @description:
 */
@Service("SearchService")
public class SearchServiceImpl implements SearchService {
    @Autowired
    FoodDao foodDao;
    @Autowired
    ShopDao shopDao;
    @Override
    public List<RelatedOption> getRelatedOptions(String word) {
        List<RelatedOption> res = new ArrayList<RelatedOption>();
        QueryWrapper<FoodEntity> queryWrapper = new QueryWrapper();
        queryWrapper.like("name",word).or().like("descr",word);
        List<FoodEntity> foodEntities = foodDao.selectList(queryWrapper);
        for (FoodEntity foodEntity : foodEntities) {
            RelatedOption relatedOption = new RelatedOption();
            relatedOption.setId(foodEntity.getId());
            relatedOption.setType("food");
            relatedOption.setWord(foodEntity.getName());
            res.add(relatedOption);
        }
        QueryWrapper<ShopEntity> queryWrapper1= new QueryWrapper();
        queryWrapper1.like("name",word).or().like("descr", word).or().like("location",word);
        List<ShopEntity> shopEntities = shopDao.selectList(queryWrapper1);
        for (ShopEntity shopEntity : shopEntities) {
            RelatedOption relatedOption = new RelatedOption();
            relatedOption.setId(shopEntity.getId());
            relatedOption.setType("shop");
            relatedOption.setWord(shopEntity.getName());
            res.add(relatedOption);
        }
        return res;
    }
}
