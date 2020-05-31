package com.logact.cateen.filter;

import com.logact.cateen.entity.ShoperEntity;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: logact
 * @date: Created in 2020/5/29 13:45
 * @description:
 */
@Slf4j
public class Check {
    public static void check(Class<?> clazz, List<?> list){
        for (int i =0 ;i<list.size();) {
            Object o = list.get(i);
            if(o.getClass()!=clazz){
                break;
            }
            try {
                Field check = clazz.getDeclaredField("check");
                check.setAccessible(true);
                Object checkObj = check.get(o);

                Integer chekcNum= (Integer) checkObj;
                log.info("checkStr:"+chekcNum);
                if(chekcNum==null||chekcNum==1){
                    list.remove(i);
                }else{
                    i++;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void check1(Class<?> clazz, List<?> list){
        for (int i =0 ;i<list.size();) {
            Object o = list.get(i);
            if(o.getClass()!=clazz){
                break;
            }
            try {
                Field check = clazz.getDeclaredField("checked");
                check.setAccessible(true);
                Object checkObj = check.get(o);

                Integer chekcNum= (Integer) checkObj;
                log.info("checkStr:"+chekcNum);
                if(chekcNum==null||chekcNum==1){
                    list.remove(i);
                }else{
                    i++;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<ShoperEntity> shopers= new ArrayList<>();
        ShoperEntity shoper = new ShoperEntity();
        shoper.setId(1);
        shoper.setCheck(1);
        ShoperEntity shoper1 = new ShoperEntity();
        shoper1.setId(2);
        shoper1.setCheck(1);
        ShoperEntity shoper2 = new ShoperEntity();
        shoper2.setId(3);
        shoper2.setCheck(null);
        shopers.add(shoper);
        shopers.add(shoper1);
        shopers.add(shoper2);
        log.info("shopers before");
        log.info(shopers.toString());
        Check.check(ShoperEntity.class, shopers);
        log.info("shopers after");
        log.info(shopers.toString());
    }
}
