package cn.nihility.context.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BeanAB {

    private final static Logger log = LoggerFactory.getLogger(BeanAB.class);

    private final BeanA beanA;
    private final BeanB beanB;

    public BeanAB(BeanA beanA, BeanB beanB) {
        this.beanA = beanA;
        this.beanB = beanB;
        log.info("BeanAB Constructor a [{}], b [{}]", beanA.hashCode(), beanB.hashCode());
    }
}
