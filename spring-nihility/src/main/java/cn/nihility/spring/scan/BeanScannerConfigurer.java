package cn.nihility.spring.scan;

import cn.nihility.spring.scan.bean.ScannerBeanClass;
import cn.nihility.spring.scan.bean.ScannerBeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Bean 扫描的具体配置工具类
 */
public class BeanScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean,
        ApplicationContextAware, BeanNameAware {

    private static final Logger log = LoggerFactory.getLogger(BeanScannerConfigurer.class);

    private String basePackages;

    private BeanNameGenerator nameGenerator;

    private String beanName;

    private ApplicationContext applicationContext;

    private ConfigurableListableBeanFactory beanFactory;

    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }


    @Override
    public void setBeanName(String name) {
        log.info("Set BeanName [{}]", name);
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(basePackages, "basePackage parameter cannot be null");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        ClassPathBeanScanner scanner = new ClassPathBeanScanner(registry);
        scanner.setBeanNameGenerator(nameGenerator);
        scanner.setResourceLoader(applicationContext);
        scanner.setScannerFactoryBeanClass(ScannerFactoryBean.class);
        scanner.addExcludeFilter((metadataReader, metadataReaderFactory) ->
                metadataReader.getClassMetadata().getClassName().equals(ScannerBeanInterface.class.getName()));
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) ->
                metadataReader.getClassMetadata().getClassName().equals(ScannerBeanClass.class.getName()));

        scanner.scan(StringUtils.tokenizeToStringArray(basePackages,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getBeanName() {
        return beanName;
    }
}
