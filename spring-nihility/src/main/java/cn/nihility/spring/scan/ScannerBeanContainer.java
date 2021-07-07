package cn.nihility.spring.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 扫描到 Bean 类的实例容器
 */
public class ScannerBeanContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScannerBeanContainer.class);

    private static final Map<String, Object> INSTANCE_CONTAINER = new ConcurrentHashMap<>();

    private ScannerBeanContainer() {}

    public static <T> T obtainInstance(Class<T> type) {
        String name = type.getName();
        LOGGER.info("obtain [{}] instance", name);
        return (T) INSTANCE_CONTAINER.computeIfAbsent(name, key -> BeanUtils.instantiateClass(type));
    }

}
