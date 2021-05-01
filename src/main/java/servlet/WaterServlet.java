package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WaterServlet extends HttpServlet {//implements Servlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("waterServlet is there");
        //将url和controller做map映射，不就是控制层了吗


        super.doGet(req, resp);
    }
}
