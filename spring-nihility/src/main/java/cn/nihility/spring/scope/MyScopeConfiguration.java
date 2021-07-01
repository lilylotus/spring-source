package cn.nihility.spring.scope;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyScopeConfiguration {

    @Bean
    public CustomScopeConfigurer myCustomScopeConfigurer(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("my", new MyScope());
        configurer.postProcessBeanFactory(configurableListableBeanFactory);
        return configurer;
    }

    @Bean
    @Scope("my")
    public MyScopeBean myScopeBean() {
        return new MyScopeBean("myScopeBean");
    }

    static class MyScopeBean {
        private String name;

        public MyScopeBean() {
        }

        public MyScopeBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MyScopeBean{" +
                    "name='" + name + '\'' +
                    "hashCode='" + hashCode() + '\'' +
                    '}';
        }
    }

}
