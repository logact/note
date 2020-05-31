package com.logact.cateen.service.page.impl;

import com.logact.cateen.dao.FoodcommentDao;
import com.logact.cateen.dao.UserDao;
import com.logact.cateen.entity.FoodcommentEntity;
import com.logact.cateen.entity.UserEntity;
import com.logact.cateen.service.page.ReviewService;
import com.logact.cateen.vo.review.ChildReview;
import com.logact.cateen.vo.review.Review;
import com.logact.cateen.vo.review.ReviewDatas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: logact
 * @date: Created in 2020/5/19 13:07
 * @description:
 */
@Service("ReviewService")
public class ReviewServiceImp implements ReviewService {
    @Autowired
    FoodcommentDao foodcommentDao;
    @Autowired
    UserDao userDao;
    @Override
    public ReviewDatas getDatas(Integer foodId) {
        ReviewDatas res= new ReviewDatas();
        List<Review> reviews = new ArrayList<Review>();
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("foodId",foodId);
        List<FoodcommentEntity> foodcommentEntities = foodcommentDao.selectByMap(condition);
        for (FoodcommentEntity foodcommentEntity : foodcommentEntities) {
            if(foodcommentEntity.getParentid()!=-1)continue;
            Review review = new Review();
            review.setId(foodcommentEntity.getId());
            review.setAuthorId(foodcommentEntity.getFrompublisherid());
            review.setContent(foodcommentEntity.getContent());
//            注入复杂对象
            UserEntity userEntity = userDao.selectById(foodcommentEntity.getFrompublisherid());

            review.setAuthor(userEntity.getUsername());
            review.setAvatar(userEntity.getAvatarimg());
            Map<String,Object> condition1=new HashMap<String, Object>();
            condition1.put("parentId",foodcommentEntity.getId());
            List<FoodcommentEntity> foodcommentEntities1 = foodcommentDao.selectByMap(condition1);
            List<ChildReview> childReviews =new ArrayList<ChildReview>();
            for (FoodcommentEntity e : foodcommentEntities1) {
                ChildReview childReview = new ChildReview();
                childReview.setId(e.getId());
                childReview.setContent(e.getContent());
                childReview.setToId(e.getTopublisherid());
                childReview.setTo(userDao.selectById(e.getTopublisherid()).getUsername());
                childReview.setFrom(userDao.selectById(e.getFrompublisherid()).getUsername());
                childReviews.add(childReview);
            }
            review.setChildReviews(childReviews);
            review.setSum(childReviews.size()+"");
            reviews.add(review);

        }
        res.setReviews(reviews);
        return res;
    }
}
