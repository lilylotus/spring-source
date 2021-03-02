package cn.nihility.context.config;

import cn.nihility.context.bean.BeanA;
import cn.nihility.context.bean.BeanB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"cn.nihility.context.bean"})
public class BeanConfiguration {

    @Bean
    public BeanA beanA() {
        return new BeanA();
    }

    @Bean
    public BeanB beanB() {
        return new BeanB();
    }

}
