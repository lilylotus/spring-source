package cn.nihility.spring.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"cn.nihility.spring.aop"})
@EnableAspectJAutoProxy
public class AspectConfiguration {
}
