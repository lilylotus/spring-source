package cn.nihility.spring.scan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Arrays;
import java.util.Set;

/**
 * 类路径的 Bean 扫描工具
 */
public class ClassPathBeanScanner extends ClassPathBeanDefinitionScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathBeanScanner.class);

    private Class<? extends ScannerFactoryBean> scannerFactoryBeanClass = ScannerFactoryBean.class;

    public ClassPathBeanScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public void setScannerFactoryBeanClass(Class<? extends ScannerFactoryBean> scannerFactoryBeanClass) {
        this.scannerFactoryBeanClass = scannerFactoryBeanClass;
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        /* org.springframework.context.annotation.ClassPathBeanDefinitionScanner.doScan */
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            LOGGER.warn("No bean was found in [{}], Please check your configuration.", Arrays.toString(basePackages));
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    /*@Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        return true;
    }*/

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return true;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();

            LOGGER.debug("Creating ScannerFactoryBean with name [{}] and [{}] mapperInterface",
                    holder.getBeanName(), beanClassName);

            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            definition.setBeanClass(this.scannerFactoryBeanClass);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            definition.setLazyInit(false);
        }
    }

}
