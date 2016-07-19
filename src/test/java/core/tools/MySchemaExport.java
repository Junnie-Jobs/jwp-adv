package core.tools;

import javax.persistence.Entity;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class MySchemaExport {
	public static org.hibernate.cfg.Configuration getConfiguration() {
		org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("next.domain")) {
			String name = bd.getBeanClassName();
			try {
				cfg.addAnnotatedClass(Class.forName(name));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		cfg.setProperty("hibernate.show_sql", "true");
		cfg.setProperty("hibernate.format_sql", "true");
		cfg.setProperty("hibernate.hbm2ddl.auto", "update");
		cfg.setProperty("hibernate.ejb.naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy");
		return cfg;
	}

	public static void main(String[] args) {
		final SchemaExport schemaExport = new SchemaExport(getConfiguration());
		schemaExport.setFormat(true);
		schemaExport.setDelimiter(";");
		schemaExport.setOutputFile("trello.sql");

		schemaExport.execute(true, false, false, true);
	}
}
