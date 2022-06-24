package com.atme.utils.my.aspect;

import com.atme.utils.my.aspect.model.annotation.ExcelOutLog;
import com.atme.utils.use.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ExcelOutLogAspect {



    private final static Logger logger = LoggerFactory.getLogger(ExcelOutLogAspect.class);

    //自定义@ExcelOutLog 注解为切点
    @Pointcut("@annotation(com.atme.utils.my.aspect.model.annotation.ExcelOutLog)")
    public void ExcelOutLog() {
    }

    /**
     * 在切点之前织入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("ExcelOutLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        //获取@ExcelOutLog注解的描述信息
        String methodDescription = getAspectLogDescription(joinPoint);
        logger.info("描述信息 : {}", methodDescription + new Date());
        //请求入参
        List list = JsonUtils.getObjectFromJson(new Gson().toJson(joinPoint.getArgs()), new TypeReference<List>() {});
//        if(list.size() == 3){
//            user = userRepository.findOne(Long.parseLong(list.get(list.size()-1).toString()));
//        }
    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("ExcelOutLog()")
    public void doAfter() throws Throwable {

    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("ExcelOutLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        try{
            logger.info("进入环绕通知");
            Object result = proceedingJoinPoint.proceed();
            Map<String, List<String>> map = JsonUtils.getObjectFromJson(new Gson().toJson(result), new TypeReference<Map<String,List<String>>>() {});
            // 保存导出操作日志记录
//            if(map.size() > 0){
//                ExcelOutRecord record = new ExcelOutRecord();
//                record.setAdminName(user.getName());
//                record.setSiteId(user.getSiteId());
//                record.setStatus((byte)1);
//                record.setColumns(map.values().toString().substring(1,map.values().toString().length()-1));
//                record.setAddTime(new Date());
//                e.save(record);
//            }
            logger.info("退出环绕通知");
            return result;

        }catch (Exception e){
            logger.info("{}",e.getMessage());
        }finally{

        }
        return null;
    }


    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder description = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description.append(method.getAnnotation(ExcelOutLog.class).description());
                    break;
                }
            }
        }
        return description.toString();
    }

}