package cn.nihility.spring.scan.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScannerBeanClass2 implements ScannerBeanInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerBeanClass2.class);

    @Override
    public void say() {
        LOGGER.info("ScannerBeanClass2 say invoked");
    }

}
