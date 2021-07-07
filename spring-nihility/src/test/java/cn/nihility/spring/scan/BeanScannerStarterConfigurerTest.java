package cn.nihility.spring.scan;

import cn.nihility.spring.scan.bean.ScannerBeanClass;
import cn.nihility.spring.scan.bean.ScannerBeanInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class BeanScannerStarterConfigurerTest {

    @Test
    void testScanner() {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(BeanScannerStarterConfigurer.class);
        ctx.refresh();

        ScannerBeanClass bean = ctx.getBean(ScannerBeanClass.class);
        assertNotNull(bean);
        bean.say();

        assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(ScannerBeanInterface.class), "xxx");
    }

}
