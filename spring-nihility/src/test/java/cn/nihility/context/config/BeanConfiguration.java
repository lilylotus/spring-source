package cn.nihility.context.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"cn.nihility.context.bean"})
public class BeanConfiguration {

}
