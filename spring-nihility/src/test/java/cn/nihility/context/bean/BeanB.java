package cn.nihility.context.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanB {

    private static final Logger log = LoggerFactory.getLogger(BeanB.class);

    public BeanB() {
        log.info("BeanB Constructor");
    }

}
