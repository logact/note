package com.logact.cateen.service.page.impl;

import com.logact.cateen.dao.FoodcommentDao;
import com.logact.cateen.dao.UserDao;
import com.logact.cateen.entity.FoodcommentEntity;
import com.logact.cateen.entity.UserEntity;
import com.logact.cateen.service.page.ReviewDetailService;
import com.logact.cateen.vo.reviewDetail.ChildReview;
import com.logact.cateen.vo.reviewDetail.Review;
import com.logact.cateen.vo.reviewDetail.ReviewDetailDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: logact
 * @date: Created in 2020/5/19 15:02
 * @description:
 */
@Service("ReviewDetailService")
public class ReviewDetailServiceImpl implements ReviewDetailService {
    @Autowired
    FoodcommentDao foodcommentDao;
    @Autowired
    UserDao userDao;
    @Override
    public ReviewDetailDatas getDatas(Integer id) {
        ReviewDetailDatas detailDatas = new ReviewDetailDatas();
        Review review=new Review();
        review.setId(id);
        FoodcommentEntity foodcommentEntity = foodcommentDao.selectById(id);
        review.setContent(foodcommentEntity.getContent());
        UserEntity publisher = userDao.selectById(foodcommentEntity.getFrompublisherid());
        review.setAuthor(publisher.getUsername());
        review.setAvatar(publisher.getAvatar());
        detailDatas.setReview(review);

        List<ChildReview> childReviews = new ArrayList<ChildReview>();
        Map<String,Object> condition = new HashMap<String, Object>();
        condition.put("parentId",id);
        List<FoodcommentEntity> foodcommentEntities = foodcommentDao.selectByMap(condition);
        for (FoodcommentEntity e : foodcommentEntities) {
            ChildReview childReview = new ChildReview();
            childReview.setId(e.getId());
            childReview.setContent(e.getContent());
            UserEntity userEntity = userDao.selectById(e.getFrompublisherid());
            childReview.setAuthor(userEntity.getUsername());
            childReview.setAvatar(userEntity.getAvatarimg());
            childReviews.add(childReview);
        }
        detailDatas.setChildReviews(childReviews);
        return detailDatas;
    }
}
