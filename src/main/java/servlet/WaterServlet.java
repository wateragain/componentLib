package servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class WaterServlet extends HttpServlet {//implements Servlet {

    private static Collection<HandlerMapping> handlerMappings;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("waterServlet is there");
        //将url和controller做map映射，不就是控制层了吗
        Object handler = getHandlerMapping(req);
        //因为这个handler可以有多种，比如Servlet或者注解
        //所以使用适配器模式

        //super.doGet(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //ClassPathXmlApplicationContext(config.getInitParXXXXX)获取web.xml里的contextConfigLocation
        //context.getBeansOfType(HandlerMapping.class)来使用spring的PostProcess获取bean

    }

    private Object getHandlerMapping(HttpServletRequest req){
        if(handlerMappings != null){
            for (HandlerMapping mapping : handlerMappings) {
                //注意对uri的处理，比如nacos的BUG
                Object handler = mapping.getHandler(req.getRequestURI());
                return handler;
            }
        }
        return null;
    }
}
