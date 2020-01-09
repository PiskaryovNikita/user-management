package com.gongsi.app.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gongsi.app.model.Role;
import com.gongsi.app.model.User;

@Configuration
@ComponentScan({"com.gongsi"})
@EnableWebMvc // configs for webmvc
@EnableTransactionManagement
@PropertySource("classpath:/ds.properties")
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private Environment env;

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/pages/", ".jsp");
	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
		org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
		builder.addAnnotatedClasses(Role.class, User.class).addProperties(cfg.getProperties());
		return builder.buildSessionFactory();
	}

	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(env.getProperty("driver"));
		ds.setUrl(env.getProperty("url"));
		ds.setUsername(env.getProperty("uname"));
		ds.setPassword(env.getProperty("password"));
		ds.setInitialSize(Integer.parseInt(env.getProperty("poolSize")));
		return ds;
	}

	@Bean
	public HibernateTransactionManager transactionManager() throws Exception {
		return new HibernateTransactionManager(sessionFactory());
	}
}
