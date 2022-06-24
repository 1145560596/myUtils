package com.atme.utils.my.aspect;

import com.atme.utils.my.aspect.model.annotation.Log;
import com.atme.utils.use.Ip2RegionUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author amao
 * @create 2022-06-22-14:19
 */
@Component
@Aspect
@Slf4j
public class LogAspect implements Ordered {

    @Autowired
    private Ip2RegionUtils ip2RegionUtils;

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE - 1;
    }

    @Pointcut("@annotation(com.atme.utils.my.aspect.model.annotation.Log)")
    private void anyMethod() {
    }

    @Around("@annotation(logAnnotation)")
    public Object log(ProceedingJoinPoint point, Log logAnnotation) throws Throwable {
        Object[] args = point.getArgs();

        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

//        User user = null;
//
//        switch (logAnnotation.mode()) {
//            case NAME_PARAM:
//                NameParam nameParam = (NameParam) args[0];
//                user = userService.findUser(nameParam.getDomain(), nameParam.getName());
//                break;
//            case SPRING_SECURITY:
//            default:
//                user = currentUserUtils.getCurrentUser();
//                break;
//        }
//
//        if (null == user) {
//            return point.proceed();
//        }
//
//        Log log = new Log();
//        log.setTypeEnum(logAnnotation.type());
//        log.setUserId(user.getId());
//        log.setName(user.getName());
//        log.setEmail(user.getEmail());
//        log.setOpName(logAnnotation.name());
//        log.setOpIp(ServletUtils.getRemoteIP(request));
//        log.setOpIpRegion(ip2RegionUtils.ip2Region(log.getOpIp()));
//        log.setDescription(logAnnotation.desc());
//        log.setGroupName(user.getGroupName());
//        log.setRoleName(user.getRoleName());
//        log.setSiteId(user.getSiteId());
//
//        systemService.save(log);
        return point.proceed();
    }


}
