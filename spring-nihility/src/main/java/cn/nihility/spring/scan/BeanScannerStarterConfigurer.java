package cn.nihility.spring.scan;

import cn.nihility.spring.scan.bean.ScannerBeanClass2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@BeanScan(basePackages = "cn.nihility.spring.scan.bean")
public class BeanScannerStarterConfigurer {

    @Bean
    public ScannerBeanClass2 scannerBeanClass2() {
        return new ScannerBeanClass2();
    }

}
