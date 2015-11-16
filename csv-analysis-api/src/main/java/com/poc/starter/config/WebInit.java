package com.poc.starter.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// Implementations of WebApplicationInitializer are automatically detected by Spring
public class WebInit implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext containerContext) throws ServletException {
		configureApp(containerContext);
		configureServlet(containerContext);
		configureSecurity();
	}
	
	private void configureApp(ServletContext containerContext) {
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();

		appContext.setDisplayName("CSV Analysis");

		// Registers the settings in AppConfig with the root context
		appContext.register(AppConfig.class);

		// Creates the Spring Container shared by all Servlets and Filters
		containerContext.addListener(new ContextLoaderListener(appContext));
	}
	
	private void configureServlet(ServletContext containerContext) {
		// Creates the dispatcher servlet context
		AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();

		// Registers the servlet configuraton with the dispatcher servlet context
		servletContext.register(ServletConfig.class);

		// Further configures the servlet context
		ServletRegistration.Dynamic dispatcher = containerContext.addServlet("spring-mvc-dispatcher", new DispatcherServlet(servletContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/*");
	}
	
	private void configureSecurity() {
	}
}