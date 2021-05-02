package servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {

    //是否支持适配
    boolean support(Object handler);

    Object handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}
