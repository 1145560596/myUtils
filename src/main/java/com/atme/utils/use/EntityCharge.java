package com.atme.utils.use;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author amao
 * @create 2022-06-20-17:49
 */
@Slf4j
public class EntityCharge {
    /**
     * 把young对象赋值给old对象
     * old对象改变
     * 必须是同一个class对象才能使用
     * @param old
     * @param young
     * @param aClass
     * @return
     */
    public static boolean setValue(Object old,Object young,Class aClass){
        Class<?> sameClass = young.getClass();
        Field[] allFields =sameClass.getDeclaredFields();
        Class<?> superClass=sameClass.getSuperclass();
        while (superClass!=null){
            allFields= ArrayUtils.addAll(allFields,superClass.getDeclaredFields());
            superClass=superClass.getSuperclass();
        }
        for (int i = 0; i < allFields.length; i++) {
            String name = allFields[i].getName();
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(name, aClass);
                Object value = descriptor.getReadMethod().invoke(young);
                if(null!=value){
                    descriptor.getWriteMethod().invoke(old,value);
                }
            }catch (Exception e){
                log.error("参数{}复制失败，错误:{}",name,e.getMessage());
            }finally {

            }
        }
        return true;
    }
}

