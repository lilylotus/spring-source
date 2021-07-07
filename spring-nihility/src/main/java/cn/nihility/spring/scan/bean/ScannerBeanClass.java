package cn.nihility.spring.scan.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScannerBeanClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerBeanClass.class);

    public void say() {
        LOGGER.info("say invoked");
    }

}
