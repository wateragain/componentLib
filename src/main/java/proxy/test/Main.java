package proxy.test;

import proxy.ProxyGenerator;

import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        //java.lang.reflect.ProxyGenerator 不是public了无法外部访问了
//        byte[] proxyClass = ProxyGenerator.generateProxyClass("$Proxy", new Class[]{MyService.class});
        byte[] proxyClass = ProxyGenerator.generateProxyClass("$Proxy", new Class[]{ImplMyService.class});
        FileOutputStream stream = new FileOutputStream(Main.class.getResource("/").getPath() + "proxy/test/$Proxy.class");
        stream.write(proxyClass);
        stream.flush();
        stream.close();
        //java动态代理的类自动实现extends Proxy，所以java的动态代理只能是接口
        //但目前实验得知继承了的还是生成文件，还是继承的函数也在？？
    }
}
