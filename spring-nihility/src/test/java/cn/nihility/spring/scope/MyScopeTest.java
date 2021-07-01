package cn.nihility.spring.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class MyScopeTest {

    @Test
    void myScopeRequireTest() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MyScopeConfiguration.class);
        ctx.refresh();

        MyScopeConfiguration.MyScopeBean bean = ctx.getBean("myScopeBean", MyScopeConfiguration.MyScopeBean.class);
        assertNotNull(bean);
        System.out.println(("获取 bean [" + bean + "]"));

        bean = ctx.getBean("myScopeBean", MyScopeConfiguration.MyScopeBean.class);
        System.out.println(("获取 bean [" + bean + "]"));

        bean = ctx.getBean("myScopeBean", MyScopeConfiguration.MyScopeBean.class);
        System.out.println(("获取 bean [" + bean + "]"));

        ctx.registerShutdownHook();
    }

}
