package scanclass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来扫描被@Component注释的类
 *
 * 注释由编译器记录在类文件中，并在运行时由VM保留，因此可以通过反射方式读取它们。
 * 可用在类，接口（包括注释类型）或枚举声明
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentScan {

    /**
     * 扫描路径
     */
    String value() default "";
}
