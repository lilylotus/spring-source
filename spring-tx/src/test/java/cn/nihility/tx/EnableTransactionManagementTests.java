package cn.nihility.tx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public class EnableTransactionManagementTests {

    private static final Logger log = LoggerFactory.getLogger(EnableTransactionManagementTests.class);

    private AnnotationConfigApplicationContext ctx;

    @BeforeEach
    public void init() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(EnableTxConfig.class, TxManagerConfig.class);
        ctx.refresh();
    }

    @AfterEach
    public void close() {
        if (ctx != null) {
            ctx.registerShutdownHook();
        }
    }

    @Test
    public void beanGenerate() {
        final Object bean = ctx.getBean(CallCountingTransactionManager.class);
        Assertions.assertNotNull(bean);
        final CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        Assertions.assertNotNull(txManager);

        final TransactionalTestBean ctxBean = ctx.getBean(TransactionalTestBean.class);
        ctxBean.saveQualifiedFoo();

        System.out.println(txManager);

        Assertions.assertEquals(1, txManager.begun);
        Assertions.assertEquals(1, txManager.commits);
        Assertions.assertEquals(0, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    /* ============================= nested ============================= */

    @Test
    public void nested() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        exc.saveNested();

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(2, txManager.commits);
        Assertions.assertEquals(0, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    @Test
    public void nestedException() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        try {
            exc.saveNestedWithException();
        } catch (Exception ex) {
            // no thing
        }

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(0, txManager.commits);
        Assertions.assertEquals(1, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    @Test
    public void nestedExceptionTryCatch() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        exc.saveNestedWithExceptionTryCatch();

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(1, txManager.commits);
        Assertions.assertEquals(1, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    /* ============================= requiresNew ============================= */

    @Test
    public void requiresNew() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);


        exc.saveRequiresNew();

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(2, txManager.commits);
        Assertions.assertEquals(0, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    @Test
    public void requiresNewException() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        try {
            exc.saveRequiresNewWithException();
        } catch (Exception ex) {
            // no thing
        }

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(0, txManager.commits);
        Assertions.assertEquals(2, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    @Test
    public void requiresNewExceptionTryCatch() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        exc.saveRequiresNewWithExceptionTryCatch();

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(1, txManager.commits);
        Assertions.assertEquals(1, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    /* ============================= required ============================= */

    @Test
    public void required() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        exc.saveRequired();

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(2, txManager.commits);
        Assertions.assertEquals(0, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    @Test
    public void requiredWithException() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        try {
            exc.saveRequiredWithException();
        } catch (Exception ex) {
            // no thing
        }

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(0, txManager.commits);
        Assertions.assertEquals(2, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }

    @Test
    public void requiredWithExceptionTryCatch() {
        CallCountingTransactionManager txManager = ctx.getBean("txManager", CallCountingTransactionManager.class);
        TransactionalTestBeanOne exc = ctx.getBean(TransactionalTestBeanOne.class);

        exc.saveRequiredWithExceptionTryCatch();

        System.out.println(txManager);

        Assertions.assertEquals(2, txManager.begun);
        Assertions.assertEquals(1, txManager.commits);
        Assertions.assertEquals(2, txManager.rollbacks);
        Assertions.assertEquals(0, txManager.inflight);
    }


    @Configuration
    @EnableTransactionManagement
    static class EnableTxConfig { }

    @Configuration
    static class TxManagerConfig {
        @Bean
        public PlatformTransactionManager txManager() {
            final CallCountingTransactionManager manager = new CallCountingTransactionManager();
            manager.setNestedTransactionAllowed(true);
            return manager;
        }

        @Bean
        public TransactionalTestBean transactionalTestBean() {
            return new TransactionalTestBean();
        }

        @Bean
        public TransactionalTestBeanOther transactionalTestBeanOther() {
            return new TransactionalTestBeanOther();
        }

        @Bean
        public TransactionalTestBeanOne transactionalTestBeanOne(TransactionalTestBeanOther other) {
            return new TransactionalTestBeanOne(other);
        }
    }

    @Service
    static class TransactionalTestBean {
        private static final Logger log = LoggerFactory.getLogger(TransactionalTestBean.class);
        @Transactional(readOnly = true)
        public Collection<?> findAllFoos() {
            log.info("findAllFoos");
            return null;
        }

        @Transactional("txManager")
        public void saveQualifiedFoo() {
            log.info("saveQualifiedFoo");
        }

        @Transactional(transactionManager = "txManager")
        public void saveQualifiedFooWithAttributeAlias() {
            log.info("saveQualifiedFooWithAttributeAlias");
        }
    }

    @Service
    static class TransactionalTestBeanOne {
        private static final Logger log = LoggerFactory.getLogger(TransactionalTestBeanOne.class);

        private final TransactionalTestBeanOther other;

        TransactionalTestBeanOne(TransactionalTestBeanOther other) {
            this.other = other;
        }

        @Transactional(readOnly = true)
        public Collection<?> findAllFoos() {
            log.info("One findAllFoos");
            other.findAllFoos();
            return null;
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequired() {
            log.info("One saveRequired");
            other.saveRequired();
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequiredWithExceptionTryCatch() {
            log.info("One saveRequiredWithExceptionTryCatch");
            try {
                other.saveRequiredWithException();
            } catch (Exception ex) {
                // ex.printStackTrace();
                log.error("saveNestedWithException, [{}]", ex.getMessage());
            }
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequiredWithException() {
            log.info("One saveRequiredWithException");
            other.saveRequiredWithException();
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveNested() {
            log.info("One saveNested");
            other.saveNested();
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveNestedWithExceptionTryCatch() {
            log.info("One saveNestedWithException");
            try {
                other.saveNestedWithException();
            } catch (Exception ex) {
                // ex.printStackTrace();
                log.error("saveNestedWithException, [{}]", ex.getMessage());
            }
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveNestedWithException() {
            log.info("One saveNestedWithExceptionTryCatch");
            other.saveNestedWithException();
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequiresNew() {
            log.info("One saveRequiresNew");
            other.saveRequiresNew();
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequiresNewWithException() {
            log.info("One saveRequiresNewWithException");
            other.saveRequiresNewWithException();
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequiresNewWithExceptionTryCatch() {
            log.info("One saveRequiresNewWithExceptionTryCatch");
            try {
                other.saveRequiresNewWithException();
            } catch (Exception ex) {
                log.error("saveRequiresNewWithExceptionTryCatch [{}]", ex.getMessage());
            }
        }

        @Transactional(transactionManager = "txManager")
        public void saveQualifiedFooWithAttributeAlias() {
            log.info("One saveQualifiedFooWithAttributeAlias");
            other.saveQualifiedFooWithAttributeAlias();
        }
    }

    @Service
    static class TransactionalTestBeanOther {
        private static final Logger log = LoggerFactory.getLogger(TransactionalTestBeanOther.class);
        @Transactional(readOnly = true)
        public Collection<?> findAllFoos() {
            log.info("Other findAllFoos");
            return null;
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequired() {
            log.info("Other saveRequired");
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
        public void saveRequiredWithException() {
            log.info("Other saveRequiredWithException");
            throw new RuntimeException("saveRequiredWithException");
        }

        @Transactional(value = "txManager", propagation = Propagation.NESTED, rollbackFor = Exception.class)
        public void saveNested() {
            log.info("Other saveNested");
        }

        @Transactional(value = "txManager", propagation = Propagation.NESTED, rollbackFor = Exception.class)
        public void saveNestedWithException() {
            log.info("Other saveNestedWithException");
            throw new RuntimeException("saveNestedWithException");
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
        public void saveRequiresNew() {
            log.info("Other saveRequiresNew");
        }

        @Transactional(value = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
        public void saveRequiresNewWithException() {
            log.info("Other saveRequiresNewWithException");
            throw new RuntimeException("saveRequiresNewWithException");
        }

        @Transactional(transactionManager = "txManager")
        public void saveQualifiedFooWithAttributeAlias() {
            log.info("Other saveQualifiedFooWithAttributeAlias");
        }
    }

}
