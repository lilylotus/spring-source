package cn.nihility.spring.scan.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScannerBeanClass2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerBeanClass2.class);

    public void say() {
        LOGGER.info("ScannerBeanClass2 say invoked");
    }

}
