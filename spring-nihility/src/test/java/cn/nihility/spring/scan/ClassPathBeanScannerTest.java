package cn.nihility.spring.scan;

import cn.nihility.spring.scan.bean.ScannerBeanClass;
import cn.nihility.spring.scan.bean.ScannerBeanClass2;
import cn.nihility.spring.scan.bean.ScannerBeanInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassPathBeanScannerTest {

    @Test
    void testScanner() {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(BeanScannerStarterConfigurer.class);
        ctx.refresh();

        ScannerBeanClass bean = ctx.getBean(ScannerBeanClass.class);
        assertNotNull(bean);
        bean.say();

        assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(ScannerBeanInterface.class), "xxx");

        ScannerBeanClass2 b2 = ctx.getBean(ScannerBeanClass2.class);
        assertNotNull(b2);
        b2.say();

//        final ScannerBeanClass sbc1 = ctx.getBean("scannerBeanClass", ScannerBeanClass.class);
//        final ScannerBeanClass sbc1 = (ScannerBeanClass) ctx.getBean("scannerBeanClass");
//        assertNull(sbc1);

//        final ScannerBeanClass sbc2 = ctx.getBean(ScannerBeanClass.class.getName(), ScannerBeanClass.class);
//        assertNotNull(sbc2);

        b2 = (ScannerBeanClass2) ctx.getBean("scannerBeanClass2");
        assertNotNull(b2);
        b2.say();

        final ScannerBeanInterface sbi1 = ctx.getBean("scannerBeanClass3", ScannerBeanInterface.class);
        assertNotNull(sbi1);
        sbi1.say();
    }

}
