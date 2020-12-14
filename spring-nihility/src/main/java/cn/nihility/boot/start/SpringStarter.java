package cn.nihility.boot.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringStarter {

	private static final Logger log = LoggerFactory.getLogger(SpringStarter.class);

	public static void main(String[] args) {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(SpringStarter.class);
		ctx.refresh();

		final InnerConfigClass bean = ctx.getBean(InnerConfigClass.class);
		System.out.println(bean);

		final Object bean1 = ctx.getBean("innerConfigClass3");
		System.out.println(bean1);

		log.info("Bean1 [{}]", bean1);

		ctx.registerShutdownHook();
	}


	@Bean(name = "innerConfigClass3")
	public InnerConfigClass innerConfigClass2() {
		return new InnerConfigClass("InnerClass");
	}

	static class InnerConfigClass {
		String name;

		public InnerConfigClass() {
		}

		public InnerConfigClass(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "InnerConfigClass{" +
					"name='" + name + '\'' +
					'}';
		}
	}

}
