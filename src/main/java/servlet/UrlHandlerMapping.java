package servlet;

import java.util.HashMap;
import java.util.Map;

public class UrlHandlerMapping implements HandlerMapping{

    private static Map<String, Object> map = new HashMap<>();

    //用InstantiationAwareBeanPostProcessor收集被@Controller这种注解了的类
    //将url和处理器放进map中，做映射。@RequestMapping

    //用这个函数获取映射的控制器
    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }
}
