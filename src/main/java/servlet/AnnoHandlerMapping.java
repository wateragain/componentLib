package servlet;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnoHandlerMapping implements HandlerMapping{
    private static Map<String, RequestMappingInfo> map = new HashMap<>();

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }

    //用InstantiationAwareBeanPostProcessor收集被@RequsetMapping这种注解了的类


    private RequestMappingInfo createRequestMappingInfo(Method method, Object bean){
        RequestMappingInfo info = new RequestMappingInfo();
        if(method.isAnnotationPresent(RequestMapping.class)){
            info.setMethod(method);
            info.setObject(bean);
            info.setUrl(method.getDeclaredAnnotation(RequestMapping.class).value());
        }
        return info;
    }
}
