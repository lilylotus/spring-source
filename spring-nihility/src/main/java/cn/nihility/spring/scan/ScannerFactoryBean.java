package cn.nihility.spring.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

public class ScannerFactoryBean<T> implements FactoryBean<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerFactoryBean.class);

    private Class<T> scannerClass;

    public ScannerFactoryBean(Class<T> scannerClass) {
        this.scannerClass = scannerClass;
    }

    @Override
    public T getObject() throws Exception {
        LOGGER.info("获取 FactoryBean 实例 [{}]", scannerClass.getName());
        return ScannerBeanContainer.obtainInstance(scannerClass);
    }

    @Override
    public Class<?> getObjectType() {
        return scannerClass;
    }

}
