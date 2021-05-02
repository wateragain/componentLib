package servlet;

public interface HandlerMapping {

    Object getHandler(String url);
}
