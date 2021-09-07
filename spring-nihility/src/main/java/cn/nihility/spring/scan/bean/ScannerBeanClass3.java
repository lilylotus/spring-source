package cn.nihility.spring.scan.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ScannerBeanClass3 implements ScannerBeanInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerBeanClass3.class);

    @Override
    public void say() {
        LOGGER.info("ScannerBeanClass3 say invoked");
    }

}
