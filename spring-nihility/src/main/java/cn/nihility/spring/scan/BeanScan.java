package cn.nihility.spring.scan;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 扫描 Bean 的路径注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(BeanScannerRegistrar.class)
public @interface BeanScan {

    /**
     * 扫描的 Bean 所在的包
     */
    String[] basePackages() default {};

    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

}
