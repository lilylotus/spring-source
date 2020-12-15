# spring tx (datasource transaction) 数据源事务管理

主要的三个对象：
* DataSource (数据源) 
    JDBCDataSource hsqldb 内存数据库连接源 
* JdbcTemplate (JDBC 操作 update/insert/delete)
* DataSourceTransactionManager (DataSource JDBC 事务管理)
* TransactionTemplate (事务模板)

## DataSourceTransactionManager

### JDBC 事务属性

<front color="red">请注意，除非开始实际的新事务，否则不会应用隔离级别和超时设置。</front>

#### 传播行为 [TransactionDefinition]

```java
new StaticTransactionDefinition(); // 默认的事务定义类 @since 5.2
```

* TransactionDefinition.PROPAGATION_REQUIRED = 0
    支持当前事务，不存在事务则新建一个事务 (默认的配置)
* TransactionDefinition.PROPAGATION_REQUIRES_NEW = 3
    创建一个新的事务，当前存在事务则暂停当前事务
* TransactionDefinition.PROPAGATION_NESTED = 6
    当前存在事务则执行一个内嵌的事务 (savePoint)

* TransactionDefinition.PROPAGATION_SUPPORTS = 1
* TransactionDefinition.PROPAGATION_MANDATORY = 2
* TransactionDefinition.PROPAGATION_NOT_SUPPORTED = 4
* TransactionDefinition.PROPAGATION_NEVER = 5

#### 隔离级别

* TransactionDefinition.ISOLATION_DEFAULT = -1
* TransactionDefinition.ISOLATION_READ_UNCOMMITTED = 1
* TransactionDefinition.ISOLATION_READ_COMMITTED = 2
* TransactionDefinition.ISOLATION_REPEATABLE_READ = 3
* TransactionDefinition.ISOLATION_SERIALIZABLE = 4

### 使用

* 实例化一个事务管理
```java
DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
```

* 获取一个事务状态
```java
// 1. 定义一个事务的属性， 传播行为/事务隔离级别/超时
DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
definition.setTimeout(30); // second
definition.setName("DataSource_Transaction");

// 2. 获取事务状态
TransactionStatus transactionStatus = transactionManager.getTransaction(definition);

// 3. 回滚
transactionManager.rollback(transactionStatus);

// 4. 提交
transactionManager.commit(transactionStatus);
```

#### 事务状态获取解析

大多数事务管理的实现都继承了 *Spring* 定义的抽象（模板）平台事务管理器

```java
public abstract class AbstractPlatformTransactionManager 
        implements PlatformTransactionManager, Serializable {}
```

```java
transactionManager.getTransaction(definition);
// 实际调用 AbstractPlatformTransactionManager.getTransaction(definition)

// 获取当前事务状态的事务对象 (AbstractPlatformTransactionManager) 由继承类具体实现
protected abstract Object doGetTransaction();

// DataSourceTransactionManager
DataSourceTransactionObject txObject = new DataSourceTransactionObject();

// 开始一个新的事务 
AbstractPlatformTransactionManager.startTransaction()
  -> DefaultTransactionStatus
```

重要的 *Spring* 事务关联模板类

* AbstractPlatformTransactionManager
    DataSourceTransactionManager

* JdbcTransactionObjectSupport
    DataSourceTransactionObject
    
重要的事务管理工具类
* TransactionSynchronizationManager
    保存 DataSource:ConnectionHolder，每个执行线程中
    ThreadLocal<Map<Object, Object>> resources = new NamedThreadLocal<>("Transactional resources");

* TransactionSynchronizationUtils

* DataSourceUtils
    DataSourceUtils.getConnection(obtainDataSource());
    DataSourceUtils.doGetConnection(datasource);
    (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource); (事务)
    
重要方法
* TransactionSynchronizationManager.getResource(key) 