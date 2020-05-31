package com.logact.shoper.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.logact.cateen.dao.FoodDao;
import com.logact.cateen.entity.*;
import com.logact.cateen.service.*;
import com.logact.cateen.vo.activity.Activity;
import com.logact.common.utils.DateUtil;
import com.logact.common.utils.Uuid;
import com.logact.shoper.service.FoodManagementService;
import com.logact.shoper.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/24 16:28
 * @description:
 */
@RestController
@RequestMapping("shoperManage")
public class ShoperMangementController {
    @Autowired
    FoodManagementService foodManagementService;
    @RequestMapping("save")
    public String save(Food food){
        return "s";
    }
    @Autowired
    ShoperService shoperService;
    @PostMapping("login")
    public String login(@RequestBody  User user ){
        System.out.println("user user user"+user);
        ShoperEntity user1 = shoperService.getOne(new QueryWrapper<ShoperEntity>().eq("username", user.getUsername()));
        if(user1!=null&&user1.getPwd().equals(user.getPassword())){
            return "success";
        }else{
            return "false";
        }

    }
    @Autowired
    ShopService shopService;
    @Autowired
    MenuService menuService;
    @Autowired
    FoodService foodService;

    @GetMapping("listMenu")
    public List<Menu> list(Integer id){
        List<Menu> res = new ArrayList<>();
        List<ShopEntity> shops = shopService.list(new QueryWrapper<ShopEntity>().eq("shoperId", id));
        System.out.println("shops:"+shops);
        for (ShopEntity shop : shops) {
            System.out.println("shop:"+shop);
            Menu menu = new Menu();
            List<MenuEntity> menuByShopId = menuService.getByShopId(shop.getId());
            for (MenuEntity menuEntity : menuByShopId) {
                if(DateUtil.isExpired(menuEntity.getDate()))continue;
                menu.setId(menuEntity.getId());
                menu.setDate(DateUtil.DateToString(menuEntity.getDate()));
                menu.setShopName(shop.getName());
                String[] ids= menuEntity.getFoodIds().split(",");
                StringBuilder sb = new StringBuilder();
                for (String s : ids) {
                    FoodEntity foodEntity = foodService.getById(Integer.parseInt(s));
                    if(foodEntity==null)continue;
                    sb.append(foodEntity.getName());
                    sb.append(" ");
                }
                menu.setMenu(sb.toString());
            }
            res.add(menu);
        }
        return res;

    }
    @GetMapping("info")
    public MenuDetail info(String id){
        MenuDetail res = new MenuDetail();
        MenuEntity menu = menuService.getByID(id);
        if(menu==null)return null;
        res.setDate(DateUtil.DateToString(menu.getDate()));
        List<SimpleFood> foods = new ArrayList<>();
        String[] ids= menu.getFoodIds().split(",");
        for (String s : ids) {
            FoodEntity foodEntity = foodService.getById(Integer.parseInt(s));
            if(foodEntity==null)continue;
            SimpleFood simpleFood = new SimpleFood();
            String images = foodEntity.getImages();
            String[] imgArr= images.split(",");
            simpleFood.setImg(imgArr[0]);
            simpleFood.setName(foodEntity.getName());
            foods.add(simpleFood);
        }
        res.setFoods(foods);
        return res;
    }
    @GetMapping("listFood")
    public List<FoodEntity> listFood(Integer shopId){
        List<FoodEntity> foodEntities = foodService.list(new QueryWrapper<FoodEntity>().eq("shopId", shopId));
        return foodEntities;
    }

    @GetMapping("listShop")
    public List<ShopEntity> listShop(Integer shoperId){
        List<ShopEntity> shopEntities = shopService.list(new QueryWrapper<ShopEntity>().eq("shoperId", shoperId));
        return shopEntities;
    }
    @PostMapping("saveMenu")
    public String savaMenu(@RequestBody MenuR menuR){
        System.out.println(menuR.getCheckedFoods());
        StringBuilder foodIds = new StringBuilder();
        for (Integer checkedFood : menuR.getCheckedFoods()) {
            foodIds.append(checkedFood);
            foodIds.append(',');
        }
        if(foodIds.charAt(foodIds.length()-1)==','){
            foodIds.deleteCharAt(foodIds.length() - 1);
        }
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setShopId(menuR.getShopId());
        menuEntity.setId(Uuid.generate());
        menuEntity.setDate(new Date());
        menuEntity.setFoodIds(foodIds.toString());
        menuService.save(menuEntity);
        System.out.println(menuR);
        return "success";
    }
    @Autowired
    ActivityService activityService;
    @PostMapping("saveActivity")
    public String saveAcitvity(@RequestBody ActivityR activityR){
        System.out.println(activityR);
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setDescr(activityR.getDesc());
        activityEntity.setInitiatorid(activityR.getShoperId());
        activityEntity.setStarttime(activityR.getDate1());
        activityEntity.setEndtime(activityR.getDate2());
        activityEntity.setTitle(activityR.getName());
        activityEntity.setSimpledesc(activityR.getSimpleDesc());
        activityEntity.setImgsrc(activityR.getPics());
        activityService.save(activityEntity);
        return "success";
    }


    @RequestMapping("/uploadPics")
    public String uploadPics(HttpServletRequest request,
                           @RequestParam("file") MultipartFile[] file) {
        System.out.println("shoperId");
        StringBuilder res = new StringBuilder();
        if (file != null && file.length > 0) {
            for (MultipartFile temp : file) {
                //将文件先保存下来
                try{
                    String originalFilename = temp.getOriginalFilename();
                    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String path =  Uuid.generate()+suffix;
                    temp.transferTo(new File("d:/" +path));
                    res.append("http://localhost:8099/pic/"+path+",");
                } catch (IOException e) {
                    e.printStackTrace();
                    return "fail";
                }
            }
            if(res.charAt(res.length()-1)==','){
                res.deleteCharAt(res.length()-1);
            }
            return res.toString();
        }
        return"fail";
    }
    @Autowired
    FoodDao foodDao;
    @PostMapping("/saveFood")
    public Integer saveFood(@RequestBody FoodEntity foodEntity){
        foodDao.insert(foodEntity);
        return 1;
    }
    public static void main(String[] args) {
        String originalFilename = "fasdfa.pnd.pic.png";
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        System.out.println(suffix);
    }


}
