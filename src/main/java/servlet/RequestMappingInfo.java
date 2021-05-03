package servlet;

import java.lang.reflect.Method;

/**
 * 将请求数据封装
 */
public class RequestMappingInfo {

    private Method method;

    private Object object;

    private String url;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
