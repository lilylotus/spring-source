package cn.nihility.spring.scan;

import org.springframework.context.annotation.Configuration;

@Configuration
@BeanScan(basePackages = "cn.nihility.spring.scan.bean")
public class BeanScannerStarterConfigurer {

}
