package pl.oakfusion.sample.webapp.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
/**
 * Implementations of this SPI will be detected automatically by {@link
 * org.springframework.web.SpringServletContainerInitializer}
 */
public class WebAppInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("pl.oakfusion.sample.webapp.config");

		/*FilterRegistration.Dynamic securityFilter = servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"));
		securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*//*");
*/

		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");

		FilterRegistration.Dynamic wro4jFilter = servletContext.addFilter("wro", new DelegatingFilterProxy("configurableWroFilter"));
		wro4jFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/bundle/*");
		wro4jFilter.setInitParameter("targetFilterLifecycle", "true");


		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.setInitParameter("defaultHtmlEscape", "true");


		DispatcherServlet servlet = new DispatcherServlet();
		// no explicit configuration reference here: everything is configured in the root container for simplicity
		servlet.setContextConfigLocation("");

		ServletRegistration.Dynamic appServlet = servletContext.addServlet("appServlet", servlet);
		appServlet.setLoadOnStartup(1);
		appServlet.setAsyncSupported(true);

		Set<String> mappingConflicts = appServlet.addMapping("/");
		if (!mappingConflicts.isEmpty()) {
			throw new IllegalStateException("'appServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
		}
	}
}