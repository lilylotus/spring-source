package cn.nihility.spring.scan;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean 扫描注册器
 */
public class BeanScannerRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(BeanScannerRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(BeanScan.class.getName()));
        if (null != attributes) {
            registryBeanDefinition(registry, attributes);
        }
    }

    private void registryBeanDefinition(BeanDefinitionRegistry registry, AnnotationAttributes attributes) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(BeanScannerConfigurer.class);

        List<String> basePackages = Arrays.stream(attributes.getStringArray("basePackages"))
                .filter(StringUtils::hasText).collect(Collectors.toList());
        String basePackagesString = StringUtils.collectionToCommaDelimitedString(basePackages);
        log.info("basePackages [{}]", basePackagesString);
        builder.addPropertyValue("basePackages", basePackagesString);

        Class<? extends BeanNameGenerator> generatorClass = attributes.getClass("nameGenerator");
        if (!BeanNameGenerator.class.equals(generatorClass)) {
            builder.addPropertyValue("nameGenerator", BeanUtils.instantiateClass(generatorClass));
        }

        String beanName = generateBeanName();
        log.info("Bean Scan Registrar name [{}]", beanName);
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

    }

    private String generateBeanName() {
        return BeanScannerRegistrar.class.getName() + "#" + BeanScan.class.getSimpleName() + "#1";
    }

}
