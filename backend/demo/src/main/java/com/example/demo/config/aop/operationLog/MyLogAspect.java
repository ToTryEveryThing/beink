package com.example.demo.config.aop.operationLog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.common.ApiResponse;
import com.example.demo.mapper.LogMapper;
import com.example.demo.pojo.Log;
import com.example.demo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/***
 * @author 睡醒继续做梦
 * @date 2023/4/30 9:45
 */

@Aspect
@Slf4j
@Component
public class MyLogAspect {

    @Autowired
    private LogMapper logMapper;

    @Pointcut("@annotation(com.example.demo.config.aop.operationLog.MyLog)")
    public void operLogPoinCut() {
    }

    @AfterReturning(returning  = "result", value = "operLogPoinCut()")
    public void saveLog(JoinPoint joinPoint, Object result){

        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        try{
            Log log1 = new Log();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            MyLog annotation = method.getAnnotation(MyLog.class);
            if (annotation != null) {
                if (request != null) {
                    log1.setType(request.getMethod());
                }
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            String sss = methodName;
            className = className.substring(className.lastIndexOf('.') + 1);
            methodName = className + "." + methodName;
            log1.setMethod(methodName); // 类名.请求方法

            log1.setCreateDate(new Date()); //操作时间
            //操作用户
            String token = request.getHeader("Authorization");
            //        判断为空
            if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
                log1.setUsername("匿名");
            }else{
                token = token.substring(7);
                Claims claims = JwtUtil.parseJWT(token);
                log1.setUsername(claims.getSubject());
            }
            log1.setIp(getRequestIp()); //请求ip
            log1.setUrl(request.getRequestURI()); // 请求URI
            //TODO  有的参数很长 会异常 先不管
            Object[] args = joinPoint.getArgs();
            System.out.println("全是参数");
            for (Object i : args){
                System.out.println(i);
            }
            if(sss.equals("getToken")||sss.equals("register"))log1.setParams("");
            else log1.setParams(Arrays.asList(joinPoint.getArgs()).toString());//请求参数
            System.out.println("请求方法为：" + methodName);
            System.out.println("请求返回内容为：" + result);
            System.out.println(result.toString());

            //TODO 垃圾方法
            String s = result.toString();
            int i = s.indexOf(',');
            int i1 = s.indexOf("=");
            log1.setResult(s.substring(i1+1,i));
            //需要先判断返回值是不是Map <String, Object>，如果不是會拋異常，需要控制层的接口返回数据格式统一
            //如果嫌返回格式统一太麻烦建议日志保存时去掉操作结果
//            log1.setResult(dataResult.get("msg").toString()); //獲取方法返回值中的msg，如果上面的類型錯誤就拿不到msg就會拋異常
            //保存日志
            log1.setId(0);
            logMapper.insert(log1);
        } catch (Exception e){
            e.printStackTrace();
            log.error("日志记录异常，请检查返回值是否是Map <String, Object>类型");
        }
    }


    /**
     * 获取json格式 请求参数
     */
    public String getJsonStrByRequest(HttpServletRequest request) {
        String param = null;
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
            if(jsonObject.equals(null)){
                param = null;
            }
            else param = jsonObject.toJSONString();
            System.out.println(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;
    }


    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

    /**
     * 获取ip
     * @return
     */
    private String getRequestIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
