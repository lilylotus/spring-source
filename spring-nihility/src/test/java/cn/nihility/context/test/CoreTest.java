package cn.nihility.context.test;

import cn.nihility.context.bean.BeanA;
import cn.nihility.context.bean.BeanB;
import cn.nihility.context.bean.CircleAB;
import cn.nihility.context.bean.CircleBA;
import cn.nihility.context.config.BeanConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CoreTest {

    private static AnnotationConfigApplicationContext ctx;

    @BeforeAll
    public static void init() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(BeanConfiguration.class);
        ctx.refresh();
    }

    @AfterAll
    public static void destroy() {
        if (null != ctx) {
            ctx.registerShutdownHook();
        }
    }

    @Test
    public void testObtainBean() {
        BeanA a = ctx.getBean(BeanA.class);
        BeanB b = ctx.getBean(BeanB.class);

        System.out.println("a hash = " + a.hashCode());
        System.out.println("b hash = " + b.hashCode());

        Assertions.assertNotNull(a);
        Assertions.assertNotNull(b);

        CircleAB circleAb = ctx.getBean("circleAB", CircleAB.class);
        CircleBA circleBA = ctx.getBean(CircleBA.class);

        System.out.println(circleAb);
        System.out.println(circleBA);
    }

}
