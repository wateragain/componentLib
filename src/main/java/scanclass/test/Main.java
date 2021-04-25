package scanclass.test;

import scanclass.ApplicationContext;
import scanclass.test.service.BeScanClass;

/**
 * 模拟spring扫描逻辑
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(ScanConfig.class);
        Object beScanClass = applicationContext.getBean("BeScanClass");
        System.out.println("beScanClass:" + beScanClass);
        System.out.println("beScanClass:" + applicationContext.getBean("BeScanClass"));
        ((BeScanClass) beScanClass).test();
    }
}
