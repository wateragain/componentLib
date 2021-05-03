package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class AnnoHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean support(Object handler) {
        return handler instanceof RequestMappingInfo;
    }

    @Override
    public Object handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        //获取请求参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) handler;
        Method method = requestMappingInfo.getMethod();
        //获取参数列表
        Parameter[] parameters = method.getParameters();

        Object[] paramValue = new Object[parameters.length];
        //进行比较，还要注意@RequestParam这种参数
        for (int i = 0; i < parameters.length; i++) {
            for (Map.Entry<String, String[]> ebtry : parameterMap.entrySet()) {
                if(ebtry.getKey().equals(parameters[i])){
                    paramValue[i] = ebtry.getValue()[0];
                }
            }
        }
        return method.invoke(requestMappingInfo.getObject(), paramValue);
    }
}
