package cn.nihility.spring.aop;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class AspectTest {

    @Test
    void testAspectRun() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AspectConfiguration.class);
        ctx.refresh();

        AspectService service = ctx.getBean("aspectService", AspectService.class);
        Integer result = service.multiplication(10, 20);
        Assertions.assertEquals(200, result);

        InvokeAspectService service2 = ctx.getBean("invokeAspectService", InvokeAspectService.class);
        Integer result2 = service2.multiplication(10, 20);
        Assertions.assertEquals(200, result2);

        ctx.registerShutdownHook();
    }

}
