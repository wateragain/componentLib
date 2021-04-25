package scanclass;

import dependencyinjection.Autowired;
import dependencyinjection.BeanNameAware;
import dependencyinjection.Scope;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    public ApplicationContext(Class configClass) {
        this.configClass = configClass;
        //扫描，得到class
        List<Class> classList = scan(configClass);
        //解析这些类BeanDefinition
        for (Class clazz : classList) {
            //如果@Component注释的是个接口怎么办
            if(clazz.isAnnotationPresent(Component.class)){
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setBeanClass(clazz);
                Component componentAnno = (Component) clazz.getAnnotation(Component.class);
                String beanName = componentAnno.value();
                if(beanName.isEmpty()){
                    //todo 1
                    beanName = clazz.getSimpleName();
                }
                String scope = "singleton";
                if (clazz.isAnnotationPresent(Scope.class)) {
                    Scope scopeAnno = (Scope) clazz.getAnnotation(Scope.class);
                    String value = scopeAnno.value();
                    if(!value.isEmpty()){
                        scope = value;
                    }
                }
                beanDefinition.setScope(scope);
                //todo 1
                beanDefinitionMap.put(beanName.toUpperCase(), beanDefinition);
            }
        }
        //基于class去创建单例bean
        instanceSingletonBean();
    }

    private List<Class> scan(Class configClass) {
        //扫描得到class
        List<Class> classList = new ArrayList<>();
        ComponentScan scanAnno = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
        if(scanAnno == null){
            throw new RuntimeException(configClass.getName() +
                    "没有找到ComponentScan注解");
        }
        String scanPath = scanAnno.value();
        if(scanPath.isEmpty()){

        }
        //去扫描target后的path里的.class文件
        scanPath = scanPath.replace(".", "/");
        ClassLoader classLoader = ApplicationContext.class.getClassLoader();
        URL url = classLoader.getResource(scanPath);
        File file = new File(url.getFile());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files) {
                String absolutePath = f.getAbsolutePath();
                absolutePath = absolutePath.substring(absolutePath.indexOf("scanclass"), absolutePath.indexOf(".class"));
                absolutePath = absolutePath.replace("\\", ".");
                try {
                    Class<?> clazz = classLoader.loadClass(absolutePath);
                    classList.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classList;
    }

    private void instanceSingletonBean(){
        //根据BD找出哪些类需要实现单例
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                //创建bean，注意在创建时会依赖注入可能重复创建
                Object bean = doCreateBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }

    public Object doCreateBean(String beanName, BeanDefinition beanDefinition){
        Object bean = null;
        //1.实例化
        Class beanClass = beanDefinition.getBeanClass();
        //这里还要选择构造方法
        try {
            bean = beanClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2.属性填充
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field f : declaredFields) {
            if (f.isAnnotationPresent(Autowired.class)) {
                //属性赋值
                try {
                    //还有根据type获取
                    Object o = getBean(f.getName());
                    f.setAccessible(true);
                    f.set(bean, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //3.Aware方法等其他你想在初始化时就执行的方法(InitializeBean,BeanPostProcessor等)
        //或者if(BeanNameAware.class.isAssignableFrom(beanClass))
        if(bean instanceof BeanNameAware){
            ((BeanNameAware) bean).setBeanName(beanName);
        }
        //BeanPostProcessor会在扫描时就将所有实现类放入list中
        //在生成bean的时候循环调用所有的
        //它有一个实现：AutowiredAnnotationBeanPostProcessor，所以其实Autowired的属性填充走的还是BeanPostProcessor
        return bean;
    }

    public Object getBean(String beanName){
        //todo 1
        beanName = beanName.toUpperCase();
        Object bean = null;
        //根据beanName找BeanDefinition，是单例的，还是原型的
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null){

        }
        if(beanDefinition.getScope().equals("prototype")){
            //创建bean
            bean = doCreateBean(beanName, beanDefinition);
        }else if(beanDefinition.getScope().equals("singleton")){
            //单例池里面去拿
            bean = singletonObjects.get(beanName);
            if(bean == null){
                bean = doCreateBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
        return bean;
    }
}
