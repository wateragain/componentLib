package dependencyinjection;

public interface BeanNameAware {

    //用来为指定属性注入bean，所以set开头
    void setBeanName(String beanName);
}
