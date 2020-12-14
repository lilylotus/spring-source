package cn.nihility.boot.tx;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

public class DataBaseTest {

	static AnnotationConfigApplicationContext ctx;
	static DataSource dataSource;
	static JdbcTemplate jdbcTemplate;
	static DataSourceTransactionManager transactionManager;

	@BeforeAll
	static void init() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(TxConfiguration.class);
		ctx.refresh();
		dataSource = ctx.getBean(DataSource.class);
		jdbcTemplate = ctx.getBean(JdbcTemplate.class);
		transactionManager = ctx.getBean(DataSourceTransactionManager.class);
	}

	@AfterAll
	static void close() {
		if (ctx != null) {
			ctx.registerShutdownHook();
		}
	}

	@Test
	public void datasource() throws IOException, SQLException {
		Assertions.assertNotNull(dataSource);

		DataScriptRunner.runScript(dataSource, "jdbc/CreateDB.sql");

		query(1);

		final DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);

		final TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
		jdbcTemplate.update("INSERT INTO subject VALUES(4, 't', 40, 200, 555, 1, CURRENT_TIMESTAMP)");
		System.out.println("----------");
		query(4);
		transactionManager.rollback(transactionStatus);
		System.out.println("----------");
		query(4);
	}

	private void query(Integer id) {
		jdbcTemplate.query("SELECT id, name, age, height, weight, active, dt FROM subject WHERE ID = ?",
				rs -> {
					System.out.println(rs.getInt("id") + ":" + rs.getString("name") +
							":" + rs.getInt("age") + ":" + rs.getInt("height") +
							":" + rs.getInt("weight") + ":" + rs.getBoolean("active") + ":" + rs.getTimestamp("dt"));
				}, id);
	}



}
